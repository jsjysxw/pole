package cn.com.avatek.pole.drawmap.uis;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.google.gson.Gson;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.avatek.pole.R;
import cn.com.avatek.pole.constant.ApiAddress;
import cn.com.avatek.pole.ctrlview.activity.PointListActivity;
import cn.com.avatek.pole.ctrlview.activity.PoleMainActivity;
import cn.com.avatek.pole.ctrlview.customview.TitleBarView;
import cn.com.avatek.pole.drawmap.bases.BaseActivity;
import cn.com.avatek.pole.drawmap.models.NodeModel;
import cn.com.avatek.pole.drawmap.models.TreeModel;
import cn.com.avatek.pole.drawmap.utils.AndroidUtil;
import cn.com.avatek.pole.drawmap.utils.AppConstants;
import cn.com.avatek.pole.drawmap.utils.DensityUtils;
import cn.com.avatek.pole.drawmap.utils.LOG;
import cn.com.avatek.pole.drawmap.views.EditAlertDialog;
import cn.com.avatek.pole.drawmap.views.RightTreeLayoutManager;
import cn.com.avatek.pole.drawmap.views.TreeView;
import cn.com.avatek.pole.drawmap.views.TreeViewItemClick;
import cn.com.avatek.pole.drawmap.views.TreeViewItemLongClick;
import cn.com.avatek.pole.entity.PlistResult;
import cn.com.avatek.pole.entity.PointResult;
import cn.com.avatek.pole.entity.SimpleResult;
import cn.com.avatek.pole.manage.NetCallBack;
import cn.com.avatek.pole.manage.NetManager;
import cn.com.avatek.pole.utils.AvatekDialog;
import cn.com.avatek.pole.utils.HLog;
import okhttp3.Call;

/**
 * Created by owant on 21/03/2017.
 */

public class EditMapActivity extends BaseActivity implements EditMapContract.View {
    private final static String TAG = "EditMapActivity";
    private final static String tree_model = "tree_model";

    private String saveDefaultFilePath;
    private String line_id;
    private EditMapContract.Presenter mEditMapPresenter;

    private TreeView editMapTreeView;
    private Button btnAddSub;
    private Button btnAddNode;
    private Button btnFocusMid;
    private Button btnCodeMode;

    private EditAlertDialog addSubNodeDialog = null;
    private EditAlertDialog addNodeDialog = null;
    private EditAlertDialog editNodeDialog = null;
    private EditAlertDialog saveFileDialog = null;
    private TitleBarView title_bar;

    @Override
    protected void onBaseIntent() {

    }

    @Override
    protected void onBasePreLayout() {

    }

    @Override
    protected int onBaseLayoutId(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_edit_think_map;
    }

    public void bindViews() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setStatusLine();
        }
        editMapTreeView = (TreeView) findViewById(R.id.edit_map_tree_view);
        btnAddSub = (Button) findViewById(R.id.btn_add_sub);
        btnAddNode = (Button) findViewById(R.id.btn_add_node);
        btnFocusMid = (Button) findViewById(R.id.btn_focus_mid);
        btnCodeMode = (Button) findViewById(R.id.btn_code_mode);

        title_bar = (TitleBarView) findViewById(R.id.title_bar);
        title_bar.setActivity(EditMapActivity.this);


        title_bar.getBtnSubmit().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePointxy();
            }
        });
    }

    @Override
    protected void onBaseBindView() {
        bindViews();

        btnAddNode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditMapPresenter.addNote();
            }
        });

        btnAddSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditMapPresenter.addSubNote();
            }
        });

        btnFocusMid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditMapPresenter.focusMid();
            }
        });

        btnCodeMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mEditMapPresenter.editNote();
            }
        });


        int dx = DensityUtils.dp2px(getApplicationContext(), 20);
        int dy = DensityUtils.dp2px(getApplicationContext(), 20);
        int screenHeight = DensityUtils.dp2px(getApplicationContext(), 720);
        editMapTreeView.setTreeLayoutManager(new RightTreeLayoutManager(dx, dy, screenHeight));

        editMapTreeView.setTreeViewItemLongClick(new TreeViewItemLongClick() {
            @Override
            public void onLongClick(View view) {

            }
        });

        editMapTreeView.setTreeViewItemClick(new TreeViewItemClick() {
            @Override
            public void onItemClick(View item) {
                mEditMapPresenter.editNote();
            }
        });

        initPresenter();
        //TODO 需要进入文件时对焦中心
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(tree_model, mEditMapPresenter.getTreeModel());
        Log.i(TAG, "onSaveInstanceState: 保持数据");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Serializable saveZable = savedInstanceState.getSerializable(tree_model);
        mEditMapPresenter.setTreeModel((TreeModel<String>) saveZable);
    }

    private void initPresenter() {
        //presenter层关联的View
        mEditMapPresenter = new EditMapPresenter(this);
        mEditMapPresenter.start();

        Intent intent = getIntent();

        Uri data = intent.getData();

        if (data != null) {
            final String path = data.getPath();
            //加载owant的文件路径
            presenterSetLoadMapPath(path);
            //解析owant文件
            mEditMapPresenter.readOwantFile();
        } else {
            String str1 = intent.getStringExtra("listResult");
            line_id = intent.getStringExtra("line_id");
            if (str1 != null && !str1.equals("")) {
                PointResult pointResult = (new Gson()).fromJson(str1, PointResult.class);
                mEditMapPresenter.createDefaultTreeModel(pointResult);
            } else {
                Toast.makeText(EditMapActivity.this, "无数据", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void presenterSetLoadMapPath(String path) {
        mEditMapPresenter.setLoadMapPath(path);
    }

    @Override
    protected void onLoadData() {
    }

    @Override
    public void setPresenter(EditMapContract.Presenter presenter) {
        if (mEditMapPresenter == null) {
            mEditMapPresenter = presenter;
        }
    }

    @Override
    public void showLoadingFile() {

    }

    @Override
    public void setTreeViewData(TreeModel<String> treeModel) {
        editMapTreeView.setTreeModel(treeModel);
    }

    @Override
    public void hideLoadingFile() {

    }

    @Override
    public void showAddNoteDialog() {
        if (editMapTreeView.getCurrentFocusNode().getParentNode() == null) {
            Toast.makeText(this, getString(R.string.cannot_add_node), Toast.LENGTH_SHORT).show();
        } else if (addNodeDialog == null) {
            LayoutInflater factory = LayoutInflater.from(this);
            View inflate = factory.inflate(R.layout.dialog_edit_input, null);
            addNodeDialog = new EditAlertDialog(EditMapActivity.this);
            addNodeDialog.setView(inflate);
            addNodeDialog.setDivTitle(getString(R.string.add_a_same_floor_node));
            addNodeDialog.addEnterCallBack(new EditAlertDialog.EnterCallBack() {
                @Override
                public void onEdit(String value) {
                    if (TextUtils.isEmpty(value)) {
                        value = getString(R.string.null_node);
                    }
                    editMapTreeView.addNode(value);
                    clearDialog(addNodeDialog);
                    if (addNodeDialog != null && addNodeDialog.isShowing())
                        addNodeDialog.dismiss();
                }
            });
            addNodeDialog.show();
        } else {
            addNodeDialog.clearInput();
            addNodeDialog.show();
        }
    }

    @Override
    public void showAddSubNoteDialog() {
        if (addSubNodeDialog == null) {
            LayoutInflater factory = LayoutInflater.from(this);
            View inflate = factory.inflate(R.layout.dialog_edit_input, null);
            addSubNodeDialog = new EditAlertDialog(this);
            addSubNodeDialog.setView(inflate);
            addSubNodeDialog.setDivTitle(getString(R.string.add_a_sub_node));
            addSubNodeDialog.addEnterCallBack(new EditAlertDialog.EnterCallBack() {
                @Override
                public void onEdit(String value) {
                    if (TextUtils.isEmpty(value)) {
                        value = getString(R.string.null_node);
                    }
                    editMapTreeView.addSubNode(value);
                    clearDialog(addSubNodeDialog);
                }
            });
            addSubNodeDialog.show();
        } else {
            addSubNodeDialog.clearInput();
            addSubNodeDialog.show();
        }
    }

    @Override
    public void showEditNoteDialog() {
        if (editNodeDialog == null) {
            LayoutInflater factory = LayoutInflater.from(this);
            View view = factory.inflate(R.layout.dialog_edit_input, null);
            editNodeDialog = new EditAlertDialog(this);
            editNodeDialog.setView(view);
            editNodeDialog.setDivTitle(getString(R.string.edit_node));
        }

        editNodeDialog.setNodeModel(getCurrentFocusNode());
        editNodeDialog.setInput(getCurrentFocusNode().getValue());
        editNodeDialog.addDeleteCallBack(new EditAlertDialog.DeleteCallBack() {
            @Override
            public void onDeleteModel(NodeModel<String> model) {
                try {
                    editMapTreeView.deleteNode(model);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onDelete() {
            }
        });
        editNodeDialog.addEnterCallBack(new EditAlertDialog.EnterCallBack() {
            @Override
            public void onEdit(String value) {
                if (TextUtils.isEmpty(value)) {
                    value = getString(R.string.null_node);
                }
                editMapTreeView.changeNodeValue(getCurrentFocusNode(), value);
                clearDialog(editNodeDialog);
            }
        });
        editNodeDialog.show();
    }

    @Override
    public void showSaveFileDialog(String fileName) {
//        if (saveFileDialog == null) {
//            LayoutInflater factory = LayoutInflater.from(this);
//            View view = factory.inflate(R.layout.dialog_edit_input, null);
//            saveFileDialog = new EditAlertDialog(this);
//            saveFileDialog.setView(view);
//            saveFileDialog.setDivTitle(getString(R.string.save_file));
//        }
//        //如果是编辑文本时可能已经有文件名了，需要进行读取文件的名字
//        saveFileDialog.setInput(mEditMapPresenter.getSaveInput());
//        saveFileDialog.setConditionDeleteTextValue(getString(R.string.exit_edit));
//
//        //获取文件目录下的已经存在的文件集合
//        saveFileDialog.setCheckLists(mEditMapPresenter.getOwantLists());
//
//        saveFileDialog.addEnterCallBack(new EditAlertDialog.EnterCallBack() {
//            @Override
//            public void onEdit(String value) {
//                //Toast.makeText(EditMapActivity.this, mEditMapPresenter.getTreeModel().getRootNode().childNodes.get(0).value, Toast.LENGTH_SHORT).show();
//
//                mEditMapPresenter.doSaveFile(value);
//
//                //退出文件
//                clearDialog(saveFileDialog);
//                EditMapActivity.this.finish();
//            }
//        });
//
//        saveFileDialog.addDeleteCallBack(new EditAlertDialog.DeleteCallBack() {
//            @Override
//            public void onDeleteModel(NodeModel<String> nodeModel) {
//
//            }
//
//            @Override
//            public void onDelete() {
//                EditMapActivity.this.finish();
//            }
//        });
//        saveFileDialog.show();
        new AlertDialog.Builder(EditMapActivity.this)
                .setTitle("提示")
                .setMessage("需要保存吗？")
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(
                                    DialogInterface dialog,
                                    int which) {
//                                mEditMapPresenter.doSaveFile("");
                                //退出文件
//                                clearDialog(saveFileDialog);
                                savePointxy();


                            }
                        })
                .setNegativeButton("退出",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(
                                    DialogInterface dialog,
                                    int which) {
                                dialog.dismiss();
                                EditMapActivity.this.finish();
                            }
                        }).create().show();
    }

    @Override
    public void focusingMid() {
        editMapTreeView.focusMidLocation();
    }

    @Override
    public String getDefaultPlanStr() {
        return getString(R.string.defualt_my_plan);
    }

    @Override
    public NodeModel<String> getCurrentFocusNode() {
        return editMapTreeView.getCurrentFocusNode();
    }

    @Override
    public String getDefaultSaveFilePath() {
        saveDefaultFilePath = Environment.getExternalStorageDirectory().getPath() + AppConstants.owant_maps;
        LOG.jLogi("saveDefaultFilePath=%s", saveDefaultFilePath);
        return saveDefaultFilePath;
    }

    @Override
    public String getAppVersion() {
        return AndroidUtil.getAppVersion(getApplicationContext());
    }

    @Override
    public void finishActivity() {
        EditMapActivity.this.finish();
    }

    private void clearDialog(Dialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //TODO 判断一下文本是否改变了
            mEditMapPresenter.saveFile();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        mEditMapPresenter.onRecycle();
        super.onDestroy();
    }

    List<PlistResult> contentBeanList = new ArrayList<>();

    private void savePointxy() {
        contentBeanList.clear();
        TreeModel<String> mTreemodel = mEditMapPresenter.getTreeModel();
        if (mTreemodel != null) {
            NodeModel<String> nodeModel = mTreemodel.getRootNode();
            if (nodeModel != null && nodeModel.getValue() != null) {
                PlistResult bean = new PlistResult();
                bean.setPoint_id(String.valueOf(nodeModel.getCuuid()));
                bean.setX(String.valueOf(nodeModel.getCoort()));
                bean.setY(String.valueOf(nodeModel.getCoorl()));
                contentBeanList.add(bean);
            }
            getChildrens(nodeModel);
        }
        subWeb();
    }

    private void getChildrens(NodeModel<String> node) {
        if (node != null && node.getChildNodes() != null && node.getChildNodes().size() > 0) {
            for (NodeModel<String> nodem : node.getChildNodes()) {
                if (nodem != null && nodem.getValue() != null) {
                    PlistResult bean = new PlistResult();
                    bean.setPoint_id(String.valueOf(nodem.getCuuid()));
                    bean.setX(String.valueOf(nodem.getCoort()));
                    bean.setY(String.valueOf(nodem.getCoorl()));
                    contentBeanList.add(bean);
                }
                getChildrens(nodem);
            }
        }

    }

    private void subWeb(){
        if(contentBeanList!=null&&contentBeanList.size()>0){
            Map<String, String> params = new HashMap<>();
            params.put("points", (new Gson()).toJson(contentBeanList));
            NetManager.sendPost(ApiAddress.point_update, params, new NetCallBack() {
                @Override
                public void onError(String error, Call call) {
                    Toast.makeText(EditMapActivity.this, "获取失败",
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess(String response) {
                    HLog.e("webnet","response="+response);
                    try {
                        Gson gson  = new Gson();
                        SimpleResult webResult = gson.fromJson(response, SimpleResult.class);
                        if(webResult.getState()>0){
                            EditMapActivity.this.finish();
                            Toast.makeText(EditMapActivity.this, "坐标更新成功", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(EditMapActivity.this, "坐标更新失败", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(EditMapActivity.this, "解析出错", Toast.LENGTH_SHORT).show();
                    }



                }
            });
        }


    }
}

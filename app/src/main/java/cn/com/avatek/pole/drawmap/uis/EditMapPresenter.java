package cn.com.avatek.pole.drawmap.uis;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;


import java.io.File;
import java.io.IOException;
import java.io.InvalidClassException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Stack;

import cn.com.avatek.pole.drawmap.models.NodeModel;
import cn.com.avatek.pole.drawmap.models.TreeModel;
import cn.com.avatek.pole.drawmap.utils.AppConstants;
import cn.com.avatek.pole.drawmap.utils.LOG;
import cn.com.avatek.pole.drawmap.utils.StringTool;
import cn.com.avatek.pole.entity.OrgBean;
import cn.com.avatek.pole.entity.PointResult;

/**
 * Created by owant on 21/03/2017.
 */
public class EditMapPresenter implements EditMapContract.Presenter {

    private EditMapContract.View mView;

    private boolean mIsCreate;
    private String mFilePath;
    private String mDefaultFilePath;
    private String mFileName;
    private TreeModel<String> mTreeModel;

    private TreeModel<String> mOldTreeModel;

    private String[] mOwantFilesArray;

    public EditMapPresenter(EditMapContract.View view) {
        mView = view;
    }

    @Override
    public void start() {
        mIsCreate = true;
        mFileName = mView.getDefaultPlanStr();
        mView.showLoadingFile();
    }

    @Override
    public void onRecycle() {
        mOwantFilesArray = null;
        mTreeModel = null;
    }

    @Override
    public void setLoadMapPath(String path) {
        // 获取到是否是编辑文件
        // 文件的名字
        // 文件路径下的owant file lists
        mIsCreate = false;

        LOG.jLogi("owant file path=%s", path);
        mFilePath = path;

        String saveFileName = path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf("."));
        mFileName = saveFileName;

        refreshOwantFilesLists();
    }

    @Override
    public void createDefaultTreeModel(PointResult pointResult) {

        list = new ArrayList<>();
        if (pointResult != null && pointResult.getContent() != null) {
            for (PointResult.ContentBean bean : pointResult.getContent()) {
                OrgBean orgBean = new OrgBean();
                orgBean.setCid(Integer.parseInt(bean.getPid()));
                orgBean.setCuuid(Integer.parseInt(bean.getPoint_id()));
                orgBean.setOrgname(bean.getNum());
                orgBean.setLeft(bean.getY());
                orgBean.setTop(bean.getX());
                list.add(orgBean);
            }
        }
        findTree(list);
        if(mTreeModel!=null && mTreeModel.getRootNode()!=null){
            mView.setTreeViewData(mTreeModel);
        }else {
            NodeModel<String> plan = new NodeModel<>(mView.getDefaultPlanStr());
            mTreeModel = new TreeModel<>(plan);
            mView.setTreeViewData(mTreeModel);
        }


//        refreshOwantFilesLists();
    }

    @Override
    public void refreshOwantFilesLists() {
        if (!TextUtils.isEmpty(mFilePath)) {
            File editFilePath = new File(mFilePath);
            String[] lists = editFilePath.getParentFile().list();

            //设置存在的owant集合
            sortFiles(lists);

        } else {
            // 默认文件路径
            mDefaultFilePath = mView.getDefaultSaveFilePath();
            File file = new File(mDefaultFilePath);
            if (!file.exists()) {
                file.mkdirs();
                LOG.jLogi("create default file path=%s", "true");
            }
            LOG.jLogi("defaultFilePath=%s", mDefaultFilePath);
            if (file.exists()) {
                String[] lists = file.list();
                sortFiles(lists);
            } else {
                LOG.jLoge("defaultPath is empty!");
            }
        }
    }

    private void sortFiles(String[] lists) {
        ArrayList<String> owantFiles = new ArrayList<>();
        for (String fileName : lists) {
            if (fileName.endsWith(".owant")) {
                LOG.jLogi("file=%s", fileName);
                fileName = fileName.substring(0, fileName.lastIndexOf("."));
                if (!StringTool.isEmpty(fileName)) {
                    owantFiles.add(fileName);
                }

                //编辑模式，不能修改文件名字
                if (!mIsCreate) {
                    owantFiles.remove(fileName);
                }
            }
        }

        if (owantFiles.size() > 0) {
            mOwantFilesArray = owantFiles.toArray(new String[owantFiles.size()]);
            if (AppConstants.CONFIG_DEBUG) {
                for (String str : mOwantFilesArray) {
                    LOG.jLogi("mOwantFilesArray str=%s", str);
                }
            }
        }

    }

    @Override
    public void readOwantFile() {
        //读取owant文件
//        if (!StringTool.isEmpty(mFilePath)) {
            try {
//                OwantFileCreate owantFileCreate = new OwantFileCreate();
//                LOG.jLogi("filePath=%s", mFilePath);
//                Object o = owantFileCreate.readContentObject(mFilePath);
//                TreeModel<String> tree = (TreeModel<String>) o;
//                mTreeModel = tree;
//                mView.setTreeViewData(mTreeModel);
//
//                mIsCreate = false;
//                //拷贝一份
//                mOldTreeModel = (TreeModel<String>) mTreeModel.deepClone();

                TreeModel<String> tree = new TreeModel<String>(new NodeModel<String>("111"));
                tree.getRootNode().setValue("根");
                mTreeModel = tree;
                mView.setTreeViewData(mTreeModel);

                mIsCreate = false;
                //拷贝一份
                mOldTreeModel = (TreeModel<String>) mTreeModel.deepClone();

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InvalidClassException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
//        }

//        TreeModel<String> tree = new TreeModel<String>(new NodeModel<String>("111"));
//        mTreeModel = tree;
//        mView.setTreeViewData(mTreeModel);
//
//        mIsCreate = false;
//        //拷贝一份
//        mOldTreeModel = (TreeModel<String>) mTreeModel.deepClone();
    }

    @Override
    public void addNote() {
        mView.showAddNoteDialog();
    }

    @Override
    public void addSubNote() {
        mView.showAddSubNoteDialog();
    }

    @Override
    public void editNote() {
        mView.showEditNoteDialog();
    }

    @Override
    public void focusMid() {
        mView.focusingMid();
    }

    @Override
    public void saveFile() {
        //TODO 进行判断是否改变了文本
        //只有在编剧模式下才进行判断其他的跳过
        boolean equals = false;
        if (!mIsCreate) {
            //进行判断
            equals = isEqualsOldTreeModel();
        }

        if (equals) {
            LOG.jLogi("no change =%s", "true");
            mView.finishActivity();
        } else {
            LOG.jLogi("change =%s", "false");
            mView.showSaveFileDialog(mFilePath);
        }
    }

    private boolean isEqualsOldTreeModel() {
        boolean equals = false;
        TreeModel<String> temp = mTreeModel;
        TreeModel<String> compareTemp = mOldTreeModel;

        StringBuffer tempBuffer = new StringBuffer();
        Stack<NodeModel<String>> stack = new Stack<>();
        NodeModel<String> rootNode = temp.getRootNode();
        stack.add(rootNode);
        while (!stack.isEmpty()) {
            NodeModel<String> pop = stack.pop();
            tempBuffer.append(pop.value);
            LinkedList<NodeModel<String>> childNodes = pop.getChildNodes();
            for (NodeModel<String> item : childNodes) {
                stack.add(item);
            }
        }

        StringBuffer compareTempBuffer = new StringBuffer();
        Stack<NodeModel<String>> stackThis = new Stack<>();
        NodeModel<String> rootNodeThis = compareTemp.getRootNode();
        stackThis.add(rootNodeThis);
        while (!stackThis.isEmpty()) {
            NodeModel<String> pop = stackThis.pop();
            compareTempBuffer.append(pop.value);
            LinkedList<NodeModel<String>> childNodes = pop.getChildNodes();
            for (NodeModel<String> item : childNodes) {
                stackThis.add(item);
            }
        }

        if (compareTempBuffer.toString().equals(tempBuffer.toString())) {
            equals = true;
        }
        return equals;
    }

    @Override
    public void doSaveFile(String fileName) {
//        OwantFileCreate owantFileCreate = new OwantFileCreate();
//        owantFileCreate.createOwantMapsDirectory();
//        owantFileCreate.createTempDirectory();
//
//        Conf conf = new Conf();
//        Date time = Calendar.getInstance().getTime();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
//        conf.date = simpleDateFormat.format(time);
//        conf.app_version = mView.getAppVersion();
//        conf.android_version = AndroidUtil.getAndroidSystemVersion();
//        conf.map_name = mTreeModel.getRootNode().getValue();
//        owantFileCreate.writeConf(conf);
//
//        owantFileCreate.writeContent(mTreeModel);
//
////        //如果是创建模式
////        if (mIsCreate) {
////            owantFileCreate.makeOwantFile(mTreeModel.getRootNode().getValue());
////        } else {
////            owantFileCreate.makeOwantFile(mFileName);
////        }
//        owantFileCreate.makeOwantFile(fileName);
//        owantFileCreate.deleteTemp();

    }

    @Override
    public void setTreeModel(TreeModel<String> treeModel) {
        mTreeModel = treeModel;
        mView.setTreeViewData(mTreeModel);
    }

    @Override
    public TreeModel<String> getTreeModel() {
        return mTreeModel;
    }

    @Override
    public List getOwantLists() {
        List list = new ArrayList();
        LOG.jLogi("isCreate=%s", mIsCreate);
        if (mOwantFilesArray != null) {
            for (String s : mOwantFilesArray) {
                list.add(s);
                LOG.jLogi("exist file=%s", s);
            }
        } else {
            LOG.jLogi("mOwantFilesArray is empty");
        }
        return list;
    }

    @Override
    public String getSaveInput() {
        if (mIsCreate) {
            return mTreeModel.getRootNode().getValue();
        } else {
            return mFileName;
        }
    }

    private List<OrgBean> list = new ArrayList<>();

    public TreeModel<String> findTree(List<OrgBean> orgBeanList) {
        List<NodeModel<String>> list1 = new ArrayList<>();
        for (OrgBean dept : orgBeanList) {
            if (dept.getCid() == -1) {
                NodeModel<String> plan = new NodeModel<>(dept.getOrgname().length()>2?dept.getOrgname().substring(0,3):dept.getOrgname());
                mTreeModel = new TreeModel<>(plan);
                plan.setCuuid(dept.getCuuid());
                int l = 0;
                int t = 0;
                try{
                    if(dept.getLeft()!=null&&!dept.getLeft().equals("")){
                        l = Integer.parseInt(dept.getLeft());
                    }
                    if(dept.getTop()!=null&&!dept.getTop().equals("")){
                        t = Integer.parseInt(dept.getTop());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                plan.setCoorl(l);
                plan.setCoort(t);
                list1.add(plan);
            }
        }
        findChildren(list1, orgBeanList);
        return mTreeModel;
    }

    private void findChildren(List<NodeModel<String>> sysDepts, List<OrgBean> depts) {
        for (NodeModel<String> sysDept : sysDepts) {
            for (OrgBean dept : depts) {
                if (sysDept.getCuuid() == dept.getCid()) {
                    NodeModel<String> addNode = new NodeModel<>(dept.getOrgname().length()>2?dept.getOrgname().substring(0,3):dept.getOrgname());
                    addNode.setCuuid(dept.getCuuid());
                    int l = 0;
                    int t = 0;
                    try{
                        if(dept.getLeft()!=null&&!dept.getLeft().equals("")){
                            l = Integer.parseInt(dept.getLeft());
                        }
                        if(dept.getTop()!=null&&!dept.getTop().equals("")){
                            t = Integer.parseInt(dept.getTop());
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    addNode.setCoorl(l);
                    addNode.setCoort(t);

                    mTreeModel.addNode(sysDept, addNode);
                }
            }
            findChildren(sysDept.getChildNodes(), depts);
        }
    }

}

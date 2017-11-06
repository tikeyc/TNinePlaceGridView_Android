# TNinePlaceGridView_Android
Android版九宫格、图片浏览等    

iOS版：https://github.com/tikeyc/TNinePlaceGridView  

![image](https://github.com/tikeyc/TikeycAndroid/blob/master/Readme/screen2.gif)      

详情访问我的简书：http://www.jianshu.com/p/99a2a72d8fef   


# 添加依赖：

###方式一：gradle
####Step 1：添加maven { url 'https://jitpack.io' } 到project的build.gradle   
```
allprojects {    
   repositories {    
      ...    
      maven { url 'https://jitpack.io' }    
   }    
}    
```
####Step 2: compile 'com.github.tikeyc.TNinePlaceGridView_Android:tnineplacegridviewlibrary:v1.1.1'到你app的build.gradle    
```
dependencies {     
    compile 'com.github.tikeyc.TNinePlaceGridView_Android:tnineplacegridviewlibrary:v1.1.1'    
}  
```

###方式二：或者直接下载完整项目，import一个module：tnineplacegridviewlibrary    

如何使用，非常之简单

List<List<Object>> imageNames2D = new ArrayList<List<Object>>();
imageNames.add(Object);
TNinePlaceGridView ninePlaceGridView = (TNinePlaceGridView) view.findViewById(R.id.ninePlaceGridView);
ninePlaceGridView.setImageNames(imageNames);
如下代码示例：

```
public class MainActivity extends AppCompatActivity {

    @ViewInject(R.id.listView)
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        x.view().inject(this);
        init();

    }

    private void init() {

        List<List<Object>> imageNames2D = new ArrayList<List<Object>>();
        for (int i = 0; i < 30; i++) {
            ArrayList<Object> imageNames = new ArrayList<Object>();
            Random random = new Random();
            for (int j = 0; j <= random.nextInt(8); j++) {
                if (j%2 == 0) {
                    imageNames.add(R.mipmap.beauty);
//                    imageNames.add("http://7xi8d6.com1.z0.glb.clouddn.com/20171011084856_0YQ0jN_joanne_722_11_10_2017_8_39_5_505.jpeg");
                } else {
                    imageNames.add(R.mipmap.glenceluanch);
//                    imageNames.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-10-10-sakura.gun_10_10_2017_12_33_34_751.jpg");
                }
            }
            imageNames2D.add(imageNames);
        }
        ListViewAdapter listViewAdapter = new ListViewAdapter(this);
        listViewAdapter.imageNames2D = imageNames2D;
        listView.setAdapter(listViewAdapter);
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    private class ListViewAdapter extends BaseAdapter {

        private Context context;
        public List<List<Object>> imageNames2D;

        public ListViewAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            if (imageNames2D != null) return imageNames2D.size();
            return 0;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        private class ViewHelper {
            CircleImageView imageViewIcon;
            TextView textViewNickName;
            TNinePlaceGridView ninePlaceGridView;

        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            ViewHelper viewHelper;
            if (view == null) {
                view = View.inflate(context,R.layout.timage_listactivity_listview_item,null);
                viewHelper = new ViewHelper();
                viewHelper.imageViewIcon = (CircleImageView) view.findViewById(R.id.imageViewIcon);
                viewHelper.textViewNickName = (TextView) view.findViewById(R.id.textViewNickName);
                viewHelper.ninePlaceGridView = (TNinePlaceGridView) view.findViewById(R.id.ninePlaceGridView);

                view.setTag(viewHelper);
            } else  {
                viewHelper = (ViewHelper) view.getTag();
            }
            List<Object> imageNames = this.imageNames2D.get(i);
            viewHelper.ninePlaceGridView.setImageNames(imageNames);

            return view;
        }
    }

}
```

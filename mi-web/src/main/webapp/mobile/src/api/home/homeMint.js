import Vue from 'vue'
import 'mint-ui/lib/style.css';
import { Tabbar, TabItem, TabContainer, TabContainerItem, DatetimePicker, Loadmore, IndexList, IndexSection, Cell, Picker,Radio ,Popup,InfiniteScroll,Swipe, SwipeItem} from 'mint-ui';
// 引入: 底部选项卡，点击 tab 会切换显示的页面。依赖 tab-item 组件。
Vue.component(Tabbar.name, Tabbar);
Vue.component(TabItem.name, TabItem);

// 引入: 面板，可切换显示子页面。
Vue.component(TabContainer.name, TabContainer);
Vue.component(TabContainerItem.name, TabContainerItem);

// 引入: 日期时间选择器，支持自定义类型。
Vue.component(DatetimePicker.name, DatetimePicker);

Vue.component(Radio.name, Radio);
// 引入: 下拉/上拉刷新，支持自定义 HTML 模板。
Vue.component(Loadmore.name, Loadmore);

// 引入: 索引列表，可由右侧索引导航快速定位到相应的内容( 通讯录 )。
Vue.component(IndexList.name, IndexList);
Vue.component(IndexSection.name, IndexSection);

// 引入: 其他组件的依赖.
Vue.component(Cell.name, Cell);
// 引入: 选择器，支持多 slot 联动。.
Vue.component(Picker.name,Picker);
// 引入: 选择器，支持多 slot 联动。.
Vue.component(Popup.name,Popup);

// 滚动分页加载。.
Vue.use(InfiniteScroll);

// 引入: 轮播图，可自定义轮播时间间隔、动画时长等。
Vue.component(Swipe.name, Swipe);
Vue.component(SwipeItem.name, SwipeItem);

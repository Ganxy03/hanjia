<template>
  <div>
    <div class="container">
      <i @click="openNav" class="el-icon-s-unfold nav-icon" style="float: left;"></i>
      <h1>This is an home page</h1>
      <div class="card-container">
        <div class="main-container">
          <div v-if="currentCard !== null || defaultCard !== null">
            <component :is="getComponentName(currentCard || defaultCard)"></component>
          </div>
        </div>
        <ul class="side-container">
          <!-- @mouseover="showCard(item.id)" -->
          <li class="side-item" v-for="item in nav_item" :key="item.id" :class="{ active: item.id === activeItem }" @click="showCard(item.id)">{{ item.name }}</li>
        </ul>
      </div>
    </div>


    <el-drawer
      title="导航"
      :visible.sync="nav_move"
      direction="ltr"
      style="width: 160%">
        <ul>
          <!-- @mouseover="showCard(item.id)" -->
          <li class="side-item" v-for="item in nav_item" :key="item.id" :class="{ active: item.id === activeItem }" @click="showCard(item.id)">{{ item.name }}</li>
        </ul>
        <div style="position: relative;">
          <div style="position: fixed;bottom: 20px;text-align: center;width: 50%;">
            <p style="">&copy;2024|Ganxy</p>
          </div>
        </div>
    </el-drawer>
  </div>
</template>
<script>
import CardContent1 from "@/views/LoginRegister.vue";
import CardContent2 from "@/views/TestView2.vue";
import CardContent3 from "@/views/TestView3.vue";
import ContentComment from "@/views/CommentView.vue";
export default {
  data() {
    return {
      activeItem: 0,
      nav_item: [
        {
          id: 0,
          name: '登陆功能',
          component: CardContent1
        },
        {
          id: 1,
          name: '评论功能',
          component: ContentComment
        },
        {
          id: 2,
          name: '邮箱验证',
          component: CardContent2
        },
        {
          id: 3,
          name: '短信验证',
          component: CardContent3
        }
      ],
      currentCard: 0,
      defaultCard: 0,
      nav_move: false,
    }
  },
  methods: {
    setActiveItem(index) {
      this.activeItem = index
    },
    showCard(id) {
      this.currentCard = id;
      this.activeItem = id
    },
    getComponentName(id) {
      return this.nav_item[id].component;
    },
    openNav() {
      this.nav_move = true
    }
  }
  
}
</script>
<style>
* {
  padding: 0;
  margin: 0;
}
.container {
  width: 100%;
  height: 100vh;
  /* background-color: blueviolet; */
  background-image: url("http://ganxy03.cn/img/background-image.jpg");
  background-size: cover;
}
.card-container {
  width: 50%;
  height: 55vh;
  margin: 20vh auto;
  perspective: 1000px;
  border-radius: 10px;
  background-color: rgb(253, 253, 253,0.6);
}
.main-container {
  float: left;
  width: 85%;
  height: 45vh;
  /* background-color: aqua; */
}
.side-container {
  margin-right: 2%;
  font-size: 11px;
  float: right;
  width: 12%;
  height: 45vh;
  border-left: 1px solid #fff;
  display: block;
  /* background-color: saddlebrown; */
}
.side-item {
  padding: 0;
  margin: 0;
  margin-top: 5px;
  list-style: none;
  cursor: pointer;
}
.side-item:hover {
  background-color: rgb(128, 0, 255);
  color: #fff;
}

.side-item.active {
  background-color: rgb(128, 0, 255);
  color: #fff;
}


.nav-icon  {
  visibility: hidden;
  font-size: 32px;
}
@media screen and (max-width: 840px) {
  .card-container {
    width: 90%;
    height: 45vh;
    margin: 15vh auto;
  }
  .main-container {
    width: 100%;
  }
  .side-container {
    display: none;
  }
  .nav-icon {
    visibility: inherit;
  }
}
</style>

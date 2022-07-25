<template>
<el-container>
  <el-header>
      <el-row>
            <el-col :span="8" class="col_l"><img src="../../assets/logo.jpg"/></el-col>
            <el-col :span="8"><h1>WoniuK15教育-后台管理系统</h1></el-col>
            <el-col :span="8" class="col_r">
                <span style="color:#fff;">您好，{{username}}</span>&nbsp;&nbsp;
                <el-button type="danger" size="small" @click="logout()">退出</el-button>
            </el-col>
        </el-row>
  </el-header>
  <el-container>
      <el-aside width="200px">
        <el-menu
          :default-active="$route.path"
          class="el-menu-vertical-demo"
          background-color="#545c64"
          text-color="#fff"
          active-text-color="#ffd04b"
          :router="true">
          <el-menu-item index="/home">
              <i class="el-icon-s-home"></i>
              <span slot="title">首页</span>
          </el-menu-item>
          <el-submenu :index="String(menu.id)" v-for="menu in menuData" :key="menu.id">
            <template slot="title">
              <i class="el-icon-star-on"></i>
              <span>{{menu.name}}</span>
            </template>
            <el-menu-item :index="'/'+item.link" v-for="item in menu.children" :key="item.id">
              <i class="el-icon-menu"></i>
              <span slot="title">{{item.name}}</span>
            </el-menu-item>
          </el-submenu> 
      </el-menu>

    </el-aside>
    <el-main>
      <router-view></router-view>
    </el-main>
  </el-container>
</el-container>
</template>

<script>
export default {
  name: 'Home',
  data(){
    return{
      username:'',
      menuData:[] 
    }
  },
  methods:{
    logout(){
      this.$confirm('此操作将退出系统, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$message({
            type: 'success',
            message: '欢迎下次光临!'
          });
          //移除localStorage里面的strToken,sessionStorage的用户
          window.localStorage.removeItem("strToken");
          window.sessionStorage.removeItem("username");
          //路由到登录
          this.$router.push('/login');
        }).catch(() => {

        });
      
    }
  },
  components: {
    
  },
  created(){
    this.username=window.sessionStorage.getItem("username");
    this.$nextTick(()=>{
//获取菜单栏数据
    this.$axios.get("/api/perms/list?username="+this.username)
        .then(res=>{
          this.menuData=res.data.data;
        }).catch(()=>{
          alert('error');
        })
    })
  }
}
</script>
<style scoped lang='less'>
.el-container {
  height: 100%;
}
.el-header {
  background: #b3c1cd;
  padding: 0;
  h1 {
    color: #fff;
    text-align: center;
    line-height: 60px;
    font-size: 26px;
  }
  .col_r {
    line-height: 60px;
    text-align: right;
    padding-right: 30px;
    a {
      color: #daa520;
    }
  }
  .col_l {
    text-align: left;
  }
}
.el-aside {
  background: #545c64;
}

.el-main {
  background: #eaeef1;
}
</style>

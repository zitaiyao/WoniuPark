<template>
    <div>
        <!-- 面包屑导航 -->
        <el-breadcrumb >
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>用户管理</el-breadcrumb-item>
            <el-breadcrumb-item>用户列表</el-breadcrumb-item>
        </el-breadcrumb>
        <!-- 搜索栏 -->
        <el-row style="margin-top:10px;">
            <el-col :span="8">
                <el-input v-model="searchText" placeholder="请输入内容">
                <!-- 放大镜按钮 -->
                    <el-button slot="append" icon="el-icon-search" @click="startQuery"></el-button>
                </el-input>
            </el-col>
            <el-col :span="8" style="margin-left:10px;">
                <el-button type="success" @click="showAddUserDialog">添加用户</el-button>
            </el-col>
            <el-col :span="8"></el-col>
        </el-row>
        <!-- 讲师列表的数据表格
            :data动态绑定属性--数组
            el-table-column 表列
         -->
        <el-table :data="userlist" style="width: 100%;margin-top:10px;">
            <el-table-column prop="id" label="序号" width="80" align="center"></el-table-column>
            <el-table-column prop="name" label="姓名" width="120" align="center"></el-table-column>
            <el-table-column prop="email" label="电子邮箱" width="200" align="center"></el-table-column>
            <el-table-column prop="status" label="状态" width="80" align="center">
                <template slot-scope="scope">
                    {{scope.row.status=='1'?'在职':'离职'}}
                </template>
            </el-table-column>
            <el-table-column label="操作" align="center" width="350">
                <template slot-scope="scope">
                    <el-button size="mini" type="primary" 
                            @click="update(scope.row)">修改</el-button>
                    <el-button size="mini" type="danger" 
                            @click="deluser(scope.row.id)">删除</el-button>
                    <el-button size="mini" type="warning" 
                            @click="openRightDialog(scope.row.id)">权限分配</el-button>
                            
                </template>
            </el-table-column>
        </el-table>
        <!-- 分页组件
        background 是否显示背景色
        layout表示显示分页的布局组件 
        prev 上一页 next下一页 pager导航页码 sizes 每页记录数
        :total设置总记录数
        :page-size每页记录数
        :current-page当前页码
        -->
        <el-pagination
                    background
                    layout="prev, pager, next"
                    :page-sizes='[5,10,15,20,25,30,35,40,45,50]'
                    :page-size=3
                    :current-page='page'
                    :total='total'
                    @current-change="changepage">
        </el-pagination>

        <el-dialog title="权限分配" :visible.sync="rightDialogFormVisible">
        <el-tree
            :data="treeData"
            show-checkbox
            :default-expand-all="true"
            node-key="id"
            :props="defaultProps">
        </el-tree>
        <div slot="footer" class="dialog-footer">
            <el-button @click="rightDialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="assignRight">确 定</el-button>
        </div>
        </el-dialog>


        <!-- 显示子组件 -->
        <!-- <component :is="comName" @func='handleShow' :subjectData="subjectData"
                                                    :objTeacher="teacher"></component> -->
    </div>
</template>

<script>
//导入子组件
// import addTeacher from './AddTeacher.vue'
// import editTeacher from './EditTeacher.vue'
export default {
   components:{
    //注册子组件
                // addTeacher,editTeacher
            },
   data() {
      return {
            userlist:[],
            searchText:'',
            page:1,
            total:0,
            rightDialogFormVisible:false,
            treeData:[],
            defaultProps:{
                children :'children',
                label:'name'
            },
            userid:0,

      }
   },
   created(){
        this.finduserList(1);
        this.$axios.get("/api/perms/all").then(res=>{
            this.treeData=res.data.data;
        });
        
   },
   methods:{
         //获取列表数据
        finduserList(page=1){
            this.page=page;
            this.$axios.get('/api/user/list',
                            {params:{
                                page:this.page,
                                searchtext:this.searchText,
                            },
                            })
                .then(res=>{
                console.log(res.data);
                this.userlist= res.data.data.list;
                this.total=res.data.data.total;   
            }).catch(e=>{
                this.$message({
                    showClose: true,
                    message: '服务器跑不见了!',
                    type: 'error',
                    offset:550,
                    duration:1000  //显示的时间,ms
                });
            });
        },
        //分页查询
        changepage(pageIndex){
            this.finduserList(pageIndex)
        },
        //带条件的分页查询
        startQuery(){
            this.finduserList(1);
        },
        showAddUserDialog(){

        },
        updateOpen(user){

        },
        deluer(id) {
            this.$confirm('您确定要删除吗?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                // 发送 axios请求
                this.$axios.get('/api/user/delete',
                                {params:{id:id}})
                    .then(res=>{
                    console.log(res.data);
                    if(res.data.status==200){
                        this.$message({
                            type: 'success',
                            message: '删除用户成功!',
                            offset:300,
                            duration:1000  //显示的时间,ms
                        });
                        //删除后刷新列表
                        this.finduserList(1);
                    }else if(res.data.status==4001){
                        this.$message({
                            type: 'error',
                            message: '没有权限',
                            offset:300,
                            duration:1000  //显示的时间,ms
                        });
                    }
                }).catch(()=>{
                    alert('error')
                });
            }).catch(()=>{});

        },
        openRightDialog(id){//打开权限分配对话框
            this.rightDialogFormVisible=true;
            //查询
            this.userid=id;
            //加载权限树,同时将当前用户的权限选中
            this.$axios.get("/api/user/permsByUserid",
                            {params:{id:id}})
                            .then(res=>{
                                this.$refs.permsTree.setCheckedNodes(res.data.data);
                                this.$nextTick(()=>{//等待DOM对象创建完成
                                    this.$refs.permsTree.setCheckedKeys(res.data.data);//设置选中
                                });
                            }).catch(()=>{

                            });       
        },
        //"确定"按钮 --> 设置权限
        assignRight(){
            //获取当前要设置权限的用户id  --> this.userid
            //获取treeData中已 选中的权限的id
            let permsids = this.$refs.permsTree.getCheckedKeys();
            //将整数id数组转换为string
            let permsidsStr = permsids.join(",");
            

            //发送axios请求,进行更新数据库
           this.$axios.post("/api/user/updateUserPerms","userid="+this.userid+"&permsids="+permsidsStr,
                            {headers:{"Content-Type":"application/x-www-form-urlencoded"}})
                      .then(res=>{
                        console.log(res);
                        if(res.data.status==200){
                            this.$message({
                                showClose:true,
                                message:'权限设置成功',
                                type:'success',
                                duration:1000
                            });
                            this.rightDialogFormVisible=false;
                        }else{
                            this.$message({
                                showClose:true,
                                message:'权限设置失败',
                                type:'error',
                                duration:1000
                            });
                        }

            }).catch(()=>{

            });

        },



    },
}
</script>

<style>

</style>

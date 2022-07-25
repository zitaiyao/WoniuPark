<template>
    <el-dialog title="编辑讲师" :visible.sync="editTeacherdialogFormVisible" @close="cancelEdit">
        <el-form :model="teacher">
            <el-form-item label="名称" :label-width="formLabelWidth">
                <el-input v-model="teacher.name" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="教育经历" :label-width="formLabelWidth">
                <el-input v-model="teacher.education" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="描述" :label-width="formLabelWidth">
                <el-input v-model="teacher.career" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="是否名师" :label-width="formLabelWidth">
                <el-switch
                    v-model="teacher.isfamous"
                    active-color="#13ce66"
                    inactive-color="#ff4949">
                </el-switch>
            </el-form-item>
            <el-form-item label="授课科目" :label-width="formLabelWidth" >
                <el-select v-model="teacher.subjectid" placeholder="请选择科目">
                    <el-option v-for="subject in subjectData" :key="subject.id"
                     :label="subject.name" :value="subject.id"></el-option>
                </el-select>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="cancelEdit">取 消</el-button>
            <el-button type="primary" @click="confirmEdit">确 定</el-button>
        </div>
    </el-dialog>
</template>

<script>
export default {
   data() {
      return {
        editTeacherdialogFormVisible:true,
        formLabelWidth:'120px',
        teacher:{}
      }
   },
   created(){
        //深拷贝
        this.teacher={'id':this.objTeacher.id,
                      'name':this.objTeacher.name,
                      'education':this.objTeacher.education,                      
                      'career':this.objTeacher.career,
                      'isfamous':true,
                      'subjectid':this.objTeacher.subjectid,
                      'status':this.objTeacher.status,}
        this.teacher.isfamous=this.objTeacher.isfamous=='y'?true:false;
   },
   props:['subjectData','objTeacher'],//使用props接收父组件传来的动态属性值
   methods:{
        cancelEdit(){
            this.editTeacherdialogFormVisible = false;
            //调用父组件传来的方法
            this.$emit('func');

        },
        confirmEdit(){
            this.teacher.isfamous=this.teacher.isfamous==true? 'y':'n';
            //发送axios的请求
            this.$axios.post('/api/teacher/edit',this.teacher)
            .then(res=>{
                if(res.data.status==200){
                    this.$message({
                        message: '编辑讲师成功!',
                        type: 'success',
                        offset:300,
                        duration:1000  //显示的时间,ms
                    });
                }else{
                    this.$message({
                        message: '编辑讲师失败!',
                        type: 'danger',
                        offset:300,
                        duration:1000  //显示的时间,ms
                    });
                }
            }).catch(()=>{
                alert('error')
            })
            this.editTeacherdialogFormVisible = false;
            //调用父组件传来的方法
            this.$emit('func');
        }
   }
}
</script>

<style>

</style>

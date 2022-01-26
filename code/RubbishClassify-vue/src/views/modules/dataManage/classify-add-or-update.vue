<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-form-item label="分类编号" prop="classifyCode">
        <el-input v-model="dataForm.classifyCode" placeholder="分类编号"></el-input>
      </el-form-item>
      <el-form-item label="分类名称" prop="classifyName">
        <el-input v-model="dataForm.classifyName" placeholder="分类名称"></el-input>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="dataForm.remark" placeholder="备注"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  import { isEmail, isMobile } from '@/utils/validate'
  export default {
    data () {
      var validatePassword = (rule, value, callback) => {
        if (!this.dataForm.id && !/\S/.test(value)) {
          callback(new Error('密码不能为空'))
        } else {
          callback()
        }
      }
      var validateComfirmPassword = (rule, value, callback) => {
        if (!this.dataForm.id && !/\S/.test(value)) {
          callback(new Error('确认密码不能为空'))
        } else if (this.dataForm.password !== value) {
          callback(new Error('确认密码与密码输入不一致'))
        } else {
          callback()
        }
      }
      var validateEmail = (rule, value, callback) => {
        if (!isEmail(value)) {
          callback(new Error('邮箱格式错误'))
        } else {
          callback()
        }
      }
      var validateMobile = (rule, value, callback) => {
        if (!isMobile(value)) {
          callback(new Error('手机号格式错误'))
        } else {
          callback()
        }
      }
      return {
        visible: false,
        roleList: [],
        dataForm: {
          id: 0,
          classifyCode: '',
          classifyName: '',
          remark:''
        },
        dataRule: {
          classifyCode: [
            { required: true, message: '分类编号不能为空', trigger: 'blur' }
          ],
          classifyName: [
             { required: true, message: '分类名不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        // 校验参数
        this.dataForm.id = id || 0
          // 设置窗口可见
           this.visible = true
          if (this.dataForm.id) {
             // 重置表单信息，避免共用表单导致数据残余
            this.$refs['dataForm'].resetFields()
            this.$http({
              url: this.$http.adornUrl(`/dataManage/classify/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.classifyCode = data.classify.classifyCode
                this.dataForm.classifyName = data.classify.classifyName
                this.dataForm.remark = data.classify.remark
              }
            })
          }else{
            // 新增操作
            console.log('新增操作执行');
          }
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/dataManage/classify/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'classifyId': this.dataForm.id || undefined,
                'classifyCode': this.dataForm.classifyCode,
                'classifyName': this.dataForm.classifyName,
                'remark': this.dataForm.remark
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>

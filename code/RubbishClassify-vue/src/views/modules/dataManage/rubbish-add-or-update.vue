<template>
  <el-dialog
    :title="!dataForm.rubbishId ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-form-item label="垃圾编号" prop="rubbishCode">
        <el-input v-model="dataForm.rubbishCode" placeholder="垃圾编号"></el-input>
      </el-form-item>

      <el-form-item label="垃圾分类" prop="classifyId">
        <el-select v-model="dataForm.classifyId" placeholder="请选择垃圾分类">
        <el-option
          v-for="item in options.classifyOption"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
      </el-form-item>

      <el-form-item label="垃圾名称" prop="rubbishName">
        <el-input v-model="dataForm.rubbishName" placeholder="垃圾名称"></el-input>
      </el-form-item>
<!--       
      <el-form-item label="图片路径" prop="picUrl">
        <el-input v-model="dataForm.picUrl" placeholder="图片路径"></el-input>
      </el-form-item> 
      -->

      <el-form-item label="图片路径" prop="picUrl">
        <el-upload
          action=""
          ref="uploadDataFormRef"
          :multiple="false"
          :limit="1"
          accept=".jpg,.jpeg,.png"
          :auto-upload="false"
          :on-exceed="onExceed"
          :http-request="httpRequest"
        >
        <el-button size="small" type="primary">点击上传</el-button>
        <h4>{{dataForm.picUrl}}</h4>
      </el-upload>
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
  export default {
    data () {
      return {
        visible: false,
        // 定义模板文件上传组件对话框
        uploadData: new FormData(),
        dataForm: {
          id: 0,
          rubbishCode: '',
          picUrl: '',
          classifyId: '',
          classifyName: '',
          rubbishName: '',
          createTime: '',
          modifyTime: '',
          remark: ''
        },
        dataRule: {
          rubbishCode: [
            { required: true, message: '垃圾编号不能为空', trigger: 'blur' }
          ],
          picUrl: [
            { required: false, message: '', trigger: 'blur' }
          ],
          classifyId: [
            { required: true, message: '垃圾分类不能为空', trigger: 'blur' }
          ],
          rubbishName: [
            { required: true, message: '垃圾名称不能为空', trigger: 'blur' }
          ],
          stock: [
            { required: true, message: '库存不能为空', trigger: 'blur' }
          ],
          vendor: [
            { required: false, message: '', trigger: 'blur' }
          ],
          remark: [
            { required: false, message: '', trigger: 'blur' }
          ],
        },
        // option定义
        options:{
          classifyOption:[]
        }
 
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          // 重置表单信息，避免共用表单导致数据残余
         this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            console.log('调用接口获取详情');
            this.$http({
              url: this.$http.adornUrl(`/dataManage/rubbish/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.rubbishCode = data.rubbish.rubbishCode
                this.dataForm.picUrl = data.rubbish.rubbishPic
                this.dataForm.classifyId = data.rubbish.classifyId
                this.dataForm.classifyName = data.rubbish.classifyName
                this.dataForm.rubbishName = data.rubbish.rubbishName
                 this.dataForm.remark = data.rubbish.remark
                this.dataForm.createTime = data.rubbish.createTime
                this.dataForm.modifyTime = data.rubbish.modifyTime
              }
            })
          }
        })

        // 加载classifyOption信息
        this.$http({
          url: this.$http.adornUrl('/dataManage/classify/getLabelLsit'),
          method: 'post',
          data: this.$http.adornData({})
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.options.classifyOption = data.classifyList
          } else {
            this.options.classifyOption = []
          }
          this.dataListLoading = false
        })
      },

      // ---------- 上传对话框方法定义 ----------
    uploadDialogHandleClose() {
      // 清空上传组件数据，关闭对话框
      this.$refs.uploadDataFormRef.clearFiles()
      this.uploadDataFormConfig.dialogVisible = false;
    },

    // 自定义文件上传
    httpRequest(param) {
      const file = param.file;
      this.uploadData.append("uploadFile", file, file.name);
    },
    // 文件过多提示
    onExceed(files, fileList) {
      this.$message.warning("上传文件数目过多，请删除多余附件！");
    },

      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            // 文件上传校验
            const uploadFileSize = this.$refs.uploadDataFormRef.uploadFiles.length;
            // if (uploadFileSize === 0) return this.$message.warning("请先上传文件！"); 不作强制要求
            if (uploadFileSize > 0) {
              const uploadFileList = this.$refs.uploadDataFormRef.uploadFiles;
              for (let i = 0; i < uploadFileList; i++) {
                const file = uploadFileList[i];
                const type = file.name.split(".")[file.name.split(".").length - 1];
                const size = file.size;
                // 校验文件格式.jpg,.jpeg,.png
                if (
                  type !== "jpg" &&
                  type !== "jpeg" &&
                  type !== "png" 
                )
                  return this.$message.warning(
                    "格式错误！请上传.xls，.xlsx，.doc，.docx格式的文件"
                  );
                if (size / 1024 / 1024 > 5)
                  return this.$message.warning("文件过大！请上传小于5MB的文件");
              }
              // 封装上传数据并上传
              // var uploadData = new FormData();
              this.$refs.uploadDataFormRef.submit();
            }
              this.uploadData.append("rubbishId", this.dataForm.id || '');
              this.uploadData.append("rubbishCode", this.dataForm.rubbishCode);
              this.uploadData.append("oldPicUrl", this.dataForm.picUrl);
              this.uploadData.append("classifyId", this.dataForm.classifyId);
              this.uploadData.append("rubbishName", this.dataForm.rubbishName);
              this.uploadData.append("remark", this.dataForm.remark);

            /*
            普通sjon交互
            this.$http({
              url: this.$http.adornUrl(`/dataManage/rubbish/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
               
              })
            */
           // 文件交互
           this.$http({
              // url: this.$http.adornUrl(`/dataManage/rubbish/${!this.dataForm.id ? 'save' : 'update'}`),
              url: this.$http.adornUrl('/dataManage/rubbish/save'),
              method: 'post',
              headers:{'Content-Type':'multipart/form-data'},
              data: this.uploadData
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    // 重置表单数据
                    this.dataForm.id = 0;
                    this.dataForm.rubbishCode = '';
                    this.dataForm.picUrl = '';
                    this.dataForm.classifyId = '';
                    this.dataForm.classifyName = '';
                    this.dataForm.rubbishName = '';
                    this.dataForm.createTime = '';
                    this.dataForm.modifyTime = '';
                    this.dataForm.remark = '';
                    this.visible = false
                    // 编辑成功之后相应重置FormData数据
                    this.uploadData = new FormData();
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

<template>
  <div class="mod-user">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.keywork" placeholder="分类编号或名称关键字" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <el-button v-if="isAuth('dataManage:classify:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        <el-button v-if="isAuth('dataManage:classify:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      v-loading="dataListLoading"
      @selection-change="selectionChangeHandle"
      style="width: 100%;">
      <el-table-column
        type="selection"
        header-align="center"
        align="center"
        width="50">
      </el-table-column>
<!-- 
      <el-table-column
        prop="classifyId"
        header-align="center"
        align="center"
        width="80"
        label="ID">
      </el-table-column>
       -->
      <el-table-column
        prop="classifyCode"
        header-align="center"
        align="center"
        label="分类编号">
      </el-table-column>
      <el-table-column
        prop="classifyName"
        header-align="center"
        align="center"
        label="分类名称">
      </el-table-column>
      <el-table-column
        prop="createTime"
        header-align="center"
        align="center"
        width="180"
        label="创建时间">
      </el-table-column>
      <el-table-column
        prop="operPermission"
        header-align="center"
        align="center"
        label="操作权限">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.operPermission == 0" size="small" type="danger">默认禁止</el-tag>
          <el-tag v-if="scope.row.operPermission == 1" size="small" type="success">允许操作</el-tag>
          <!-- <el-tag v-else size="small">其他</el-tag> -->
        </template>
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <!-- 限定操作权限，禁止对默认数据进行编辑（操作权限限定为1方可进行操作） -->
          <el-button v-if="isAuth('dataManage:classify:update')&&scope.row.operPermission==1" type="text" size="small" @click="addOrUpdateHandle(scope.row.classifyId)">修改</el-button>
          <el-button v-if="isAuth('dataManage:classify:delete')&&scope.row.operPermission==1" type="text" size="small" @click="deleteHandle(scope.row.classifyId)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
      :current-page="pageIndex"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pageSize"
      :total="totalPage"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
  </div>
</template>

<script>
  import AddOrUpdate from './classify-add-or-update'
  export default {
    data () {
      return {
        dataForm: {
          keywork: ''
        },
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        addOrUpdateVisible: false
      }
    },
    components: {
      AddOrUpdate
    },
    activated () {
      this.getDataList()
    },
    methods: {
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/dataManage/classify/list'),
          method: 'post',
          data: this.$http.adornData({
            'page': this.pageIndex,
            'limit': this.pageSize,
            // 请求业务参数
            'keywork': this.dataForm.keywork
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.dataList = data.page.list
            this.totalPage = data.page.totalCount
          } else {
            this.dataList = []
            this.totalPage = 0
          }
          this.dataListLoading = false
        })

      },
      // 每页数
      sizeChangeHandle (val) {
        this.pageSize = val
        this.pageIndex = 1
        this.getDataList()
      },
      // 当前页
      currentChangeHandle (val) {
        this.pageIndex = val
        this.getDataList()
      },
      // 多选
      selectionChangeHandle (val) {
        this.dataListSelections = val
      },
      // 新增 / 修改
      addOrUpdateHandle (id) {
        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id)
        })
      },
      // 删除
      deleteHandle (id) {
        var classifyIds = id ? [id] : this.dataListSelections.map(item => {
          return item.classifyId
        })
        this.$confirm(`确定对[id=${classifyIds.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/dataManage/classify/delete'),
            method: 'post',
            data: this.$http.adornData({
              classifyIdList:classifyIds
            }, false)
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.getDataList()
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        }).catch(() => {})
      }
    }
  }
</script>

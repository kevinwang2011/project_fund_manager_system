<template>
  <div>
    <div style="margin-left: 5%; margin-bottom: 2%;margin-top: 2%;">
      <el-form>
        登录名：<el-input style="width:200px;margin-right: 5%;" clearable v-model="userm.userId" autocomplete="off" placeholder="请输入职工号或学生号" />
        用户姓名：<el-input style="width:200px;margin-right: 5%;" clearable v-model="userm.userName" autocomplete="off" placeholder="请输入姓名" />
        用户状态：<el-select @change="query" style="width:200px;" v-model="userm.userStatus" placeholder="请选择">
          <el-option label="已停用" value="0" />
          <el-option label="待审核" value="1" />
          <el-option label="已通过" value="2" /></el-select>
        <br /><el-button style="float:right;margin-right: 5%;" type="primary" @click="query">查询</el-button>
      </el-form>
    </div>
  <el-table
    border="true"
    :data="tableData"
    style="width: 90%; margin-left: 5%"
    :row-class-name="tableRowClassName">
    <el-table-column
      prop="userId"
      label="登录名"
      width="180">
    </el-table-column>
    <el-table-column prop="userName"
      label="用户姓名"
      width="180">
    </el-table-column>
    <el-table-column
      :formatter="cStatus"
      prop="userStatus"
      label="用户状态">
    </el-table-column>
    <el-table-column
      :formatter="cMark"
      prop="userMark"
      label="用户身份">
    </el-table-column>
    <el-table-column
      prop="createTime"
      label="注册时间"
      width="170">
    </el-table-column>
    <el-table-column label="操作" width="380">
      <template slot-scope="scope">
        <el-button
          size="mini"
          @click="open(scope.$index, scope.row)">{{scope.row.userStatus===2?'停用':(scope.row.userStatus===1?'通过':'启用')}}</el-button>
        <el-button
          size="mini"
          @click="openpwd(scope.$index, scope.row)">{{scope.row.userStatus===0?'删除':(scope.row.userStatus===1?'退回':'修改密码')}}</el-button>
        <el-button
          v-if="scope.row.userStatus===2"
          size="mini"
          @click="relat(scope.$index, scope.row)">关联项目</el-button>
        <el-button
          v-if="scope.row.userStatus===2"
          size="mini"
          @click="cancelRelat(scope.$index, scope.row)">取消关联</el-button>
      </template>
    </el-table-column>
  </el-table>
    <div style="margin-top: 2%; margin-bottom: 5%;text-align: center">
      <el-pagination
        background
        @current-change="handleCurrentChange"
        :current-page="this.pagec"
        layout="prev, pager, next"
        :total=da.size>
      </el-pagination>
    </div>
    <el-dialog title="项目关联" :visible.sync="dialogFormVisible" width="25%">
      <el-autocomplete
        style="width:100%"
        v-model="state"
        :fetch-suggestions="querySearchAsync"
        placeholder="请输入项目名称"
        @select="handleSelect"
        clearable
      ></el-autocomplete>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="projectRelat">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="取消关联" :visible.sync="cancelDialogFormVisible" width="25%">
      <el-autocomplete
        style="width:100%"
        v-model="state1"
        :fetch-suggestions="querySearchAsync1"
        placeholder="请输入项目名称"
        @select="handleSelect"
        clearable
      ></el-autocomplete>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelDialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="cancelProjectRelat">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import axios from 'axios'
import { getToken } from '@/utils/auth'
export default {
  name: 'Usermanager',
  created() {
    // eslint-disable-next-line eqeqeq   headers['X-Token']
    this.da.size = 0
    this.query(this.pagec)

  },
  mounted() {
    this.loadAll()
  },
  methods: {
    loadAll() {
      const data ={}
      axios.post(this.url + '/project/queryList', data, {
        headers: { 'X-Token': getToken() }
      })
        .then(res => {
          if (res.data.code === 20000) {
            this.restaurants = res.data.data
          } else {
          }
        })
    },
    relat(index, row) {
      this.dialogFormVisible = true
      this.userId = row.userId
      this.userName = row.userName
      this.relatNum = 1
    },
    cancelRelat(index, row) {
      this.relatNum = 1
      this.userId = row.userId
      this.userName = row.userName
      this.state1=''
      this.restaurants2=[]
      const data = {'userId': this.userId, 'desc': 1}
      axios.post(this.url + '/projectRelat/queryList', data, {
        headers: { 'X-Token': getToken() }
      })
        .then(res => {
          if (res.data.code === 20000) {
            this.restaurants2 = res.data.data
          } else {
          }
          if(res.data.totalSize === 0){
            this.cancelDialogFormVisible = false
            this.$message({
              message: '该账户:'+this.userId+'没有关联的项目',
              type: 'success'
            })
            scope.row.isVisible = 'none'
          }else{
            this.cancelDialogFormVisible = true
          }
        })
    },
    querySearchAsync(queryString, cb) {
      var restaurants = this.restaurants
      var results = queryString ? restaurants.filter(this.createStateFilter(queryString)) : restaurants
      clearTimeout(this.timeout)
      this.timeout = setTimeout(() => {
        cb(results)
      }, 3000 * Math.random())
    },
    querySearchAsync1(queryString, cb) {
      var restaurants = this.restaurants2
      var results = queryString ? restaurants.filter(this.createStateFilter1(queryString)) : restaurants
      clearTimeout(this.timeout)
      this.timeout = setTimeout(() => {
        cb(results)
      }, 3000 * Math.random())
    },
    createStateFilter(queryString) {
      return (state) => {
        return (state.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0)
      }
    },
    createStateFilter1(queryString) {
      return (state1) => {
        return (state1.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0)
      }
    },
    handleSelect(item) {
      this.projectId = item.projectId
    },
    projectRelat() {
      this.dialogFormVisible = false
      const data = { 'userId': this.userId, 'projectId': this.projectId, 'userName': this.userName }
      if (this.relatNum === 1) {
        this.relatNum = 0
        axios.post(this.url + '/projectRelat/relat', data, {
          headers: { 'X-Token': getToken() }
        })
          .then(res => {
            if (res.data.code === 20000) {
              this.$message({
                message: res.data.desc,
                type: 'success'
              })
            } else {
              this.$message.error(res.data.desc)
            }
          })
      }
    },
    cancelProjectRelat() {
      this.cancelDialogFormVisible = false
      const data = { 'userId': this.userId, 'projectId': this.projectId, 'userName': this.userName }
      if (this.relatNum === 1) {
        this.relatNum = 0
        axios.post(this.url + '/projectRelat/cancelRelat', data, {
          headers: { 'X-Token': getToken() }
        })
          .then(res => {
            if (res.data.code === 20000) {
              this.restaurants2 = []
              this.$message({
                message: res.data.desc,
                type: 'success'
              })
            } else {
              this.$message.error(res.data.desc)
            }
          })
      }
    },
    handleCurrentChange(val) {
      this.pagec = val
      this.query()
    },
    query() {
      if (this.stat === this.userm.userStatus) {
      } else {
        this.stat = this.userm.userStatus
        this.pagec = 1
      }
      this.tableData = []
      const data = this.userm
      data.pageSize = 10
      data.pageCount = this.pagec
      axios.post(this.url + '/user/queryPage', data, {
        headers: { 'X-Token': getToken() }
      })
        .then(res => {
          this.da.size = 0
          this.tableData = []
          if (res.data.code === 20000) {
            this.tableData = res.data.data
            this.da.size = res.data.totalSize
            this.$message({
              message: res.data.desc,
              type: 'success'
            })
          } else {
            this.da.size = 0
            this.tableData = []
            this.$message.error(res.data.desc)
          }
        })
    },
    cStatus(row, column) {
      return row.userStatus === 2 ? '已通过' : row.userStatus === 1 ? '待审核' : '已停用'
    },
    cMark(row, column) {
      return row.userMark === 0 ? '教师' : '学生'
    },
    tableRowClassName({ row, rowIndex }) {
      if (row.userStatus === 0) {
        return 'error-row'
      } else if (row.userStatus === 2) {
        return 'success-row'
      }
      return 'warning-row'
    },
    handleEdit(index, row) {
      this.showText = ''
      //  scope.row.userStatus===2?'停用':(scope.row.userStatus===1?'通过':'启用')
      //  console.log(index, row)
      const data = { 'userId': row.userId }
      data.desc = row.userStatus === 1 ? '2' : (row.userStatus === 1 ? '2' : '0')
      if (row.userStatus === 1) {
        data.desc = 2
      } else if (row.userStatus === 2) {
        data.desc = 0
      } else {
        data.desc = 2
      }
      if (this.openNum === 1) {
        this.openNum = 0
        axios.post(this.url + '/user/update', data, {
          headers: { 'X-Token': getToken() }
        })
          .then(res => {
            if (res.data.code === 20000) {
              this.$message({
                message: res.data.desc,
                type: 'success'
              })
              this.query(this.pagec)
            } else {
              this.$message.error(res.data.desc)
            }
          })
      }
    },
    handleDelete(index, row) {
      //  scope.row.userStatus===0?'删除':(scope.row.userStatus===1?'退回':'修改密码')
      //  console.log(index, row)
      const data = { 'userId': row.userId }
      let uurl = ''
      if (row.userStatus === 2) {
        uurl = '/user/update'
        data.desc = 3
        if (this.openNum === 1) {
          data.userPwd = this.pwd
        }
      } else if (row.userStatus === 1) {
        uurl = '/user/deleteById'
        data.desc = 1
      } else {
        uurl = '/user/deleteById'
        data.desc = 0
      }
      if (this.openNum === 1) {
        this.openNum = 0
        axios.post(this.url + uurl, data, {
          headers: { 'X-Token': getToken() }
        })
          .then(res => {
            if (res.data.code === 20000) {
              this.$message({
                message: res.data.desc,
                type: 'success'
              })
              this.query(this.pagec)
            } else {
              this.$message.error(res.data.desc)
            }
          })
      }
    },
    open(index, row) {
      if (row.userStatus === 1) {
        this.showText = '此操作将更改用户状态为：已启用, 是否继续?'
      } else if (row.userStatus === 2) {
        this.showText = '此操作将更改用户状态为：已停用, 是否继续?'
      } else {
        this.showText = '此操作将更改用户状态为：已启用, 是否继续?'
      }
      this.$confirm(this.showText, '风险提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.openNum = 1
        this.handleEdit(index, row)
      }).catch(() => {
        this.openNum = 0
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
    },
    openpwd(index, row) {
      if (row.userStatus === 2) {
        this.showText = '此操作将强制更改用户密码, 是否继续?'
      } else if (row.userStatus === 1) {
        this.showText = '此操作将退回用户申请, 是否继续?'
      } else {
        this.showText = '此操作将不可恢复的删除用户数据, 是否继续?'
      }
      if (row.userStatus === 2) {
        this.$prompt('请输入密码', '更改密码', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          inputErrorMessage: '密码以数字、英文字母或数字组成'
        }).then(({ value }) => {
          this.pwd = value
          this.openNum = 1
          this.handleDelete(index, row)
        }).catch(() => {
          this.openNum = 0
          this.$message({
            type: 'info',
            message: '取消输入'
          })
        })
      } else {
        this.$confirm(this.showText, '风险提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.openNum = 1
          this.handleDelete(index, row)
        }).catch(() => {
          this.openNum = 0
          this.$message({
            type: 'info',
            message: '已取消'
          })
        })
      }
    }
  },
  data() {
    return {
      userm: {
        userId: '',
        userName: '',
        userStatus: '1'
      },
      userId:'',
      restaurants: [],
      restaurants2: [],
      state: '',
      state1: '',
      timeout:  null,
      projectId: '',
      userName: '',
      showStatus: 0,
      pagec: 1,
      relatNum: 0,
      showText: '',
      openNum: 0,
      pwd: '',
      stat: 1,
      tableData: [],
      da: {
        size: ''
      },
      relatStatus: 0,
      dialogFormVisible: false,
      cancelDialogFormVisible: false
    }
  }
}
</script>

<style>
  .el-table .warning-row {
    background: #fbfde2;
  }

  .el-table .success-row {
    background: #daf9dc;
  }
  .el-table .error-row {
    background: #f9e3ee;
  }
</style>

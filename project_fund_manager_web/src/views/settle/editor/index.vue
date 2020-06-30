<template>
  <div>
    <div style="margin-left: 5%; margin-bottom: 2%;margin-top: 2%;">
      <el-form>
        项目编号：<el-input style="width:200px;margin-right: 5%;" clearable v-model="projectl.projectId" autocomplete="off" placeholder="请输入项目编号" />
        项目名称：<el-input style="width:200px;margin-right: 5%;" clearable v-model="projectl.projectName" autocomplete="off" placeholder="请输入项目名称" />
        <el-button style="margin-right: 5%;" type="primary" @click="query">查询</el-button>
      </el-form>
    </div>
    <el-table
      :data="tableData"
      style="width: 90%; margin-left: 5%"
      :row-class-name="tableRowClassName">
      <el-table-column
        fixed="left"
        align="center"
        prop="projectId"
        label="项目编号"
        width="180">
      </el-table-column>
      <el-table-column
        fixed="left"
        align="center"
        prop="projectName"
        show-overflow-tooltip="true"
        label="项目名称">
      </el-table-column>
      <el-table-column
        align="center"
        prop="projectGroup"
        show-overflow-tooltip="true"
        label="项目成员"
        width="270">
      </el-table-column>
      <el-table-column
        align="center"
        width="150"
        prop="projectBudget"
        @click="updateBudget"
        label="项目预算">
      </el-table-column>
      <el-table-column
        align="center"
        width="150"
        prop="projectBalance"
        label="项目余额">
      </el-table-column>
      <el-table-column
        align="center"
        width="150"
        prop="projectTotalAmount"
        label="已用额度">
      </el-table-column>
      <el-table-column
        width="153"
        align="center"
        prop="createTime"
        label="立项时间">
      </el-table-column>
      <el-table-column label="操作" fixed="right" width="110" :formatter="isVisibleFun">
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.projectStatus==0"
            size="mini"
            type="primary"
            @click="open(scope.$index, scope.row)">申请结算
          </el-button>
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
    <el-dialog title="项目预算转移" :visible.sync="dialogFormVisible">
      <el-form :model="moveform" style="margin-left: 25%;width: 400px;">
        <el-form-item label="项目编号：" :label-width="formLabelWidth">
          <el-input v-model="moveform.projectId" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="转移额度：" :label-width="formLabelWidth">
          <el-input v-model="moveform.projectBanalce" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="moveconfign">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import axios from 'axios'
import { getToken } from '@/utils/auth'
export default {
  name: 'ProjectOverview',
  data() {
    return {
      dialogFormVisible: false,
      moveform: {
        projectId: '',
        projectBanalce: ''
      },
      formLabelWidth: '100px',
      isVisible: 'none', // 按钮隐藏   :style="{ display: isVisible }"
      projectl: {
        projectId: '',
        projectName: '',
        projectStatus: '1'
      },
      showStatus: 0,
      pagec: 1,
      showText: '',
      openNum: 0,
      pwd: '',
      stat: 1,
      tableData: [
        {
          'projectId': 'JCUT-2020-GZM-02-01-0',
          'projectName': '这是一个特别长的系统设计与开发名称',
          'projectGroup': '欧阳娜娜，关晓彤，买瓶，付敏，江小白',
          'projectStatus': 0,
          'projectBudget': '2.00万元',
          'projectBalance': '1.0001万元',
          'projectTotalAmount': '9999元',
          'createTime': '2020-01-01 23:59:59'
        },
        {
          'projectId': 'JCUT-2020-GZM-02-01-1',
          'projectName': '这是一个特别长的系统设计与开发名称',
          'projectGroup': '欧阳娜娜，关晓彤，买瓶，付敏，江小白',
          'projectStatus': 0,
          'projectBudget': '2.00万元',
          'projectBalance': '1.0001万元',
          'projectTotalAmount': '9999元',
          'createTime': '2020-01-01 23:59:59'
        },
        {
          'projectId': 'JCUT-2020-GZM-02-01-2',
          'projectName': '这是一个特别长的系统设计与开发名称',
          'projectGroup': '欧阳娜娜，关晓彤，买瓶，付敏，江小白',
          'projectStatus': 0,
          'projectBudget': '2.00万元',
          'projectBalance': '1.0001万元',
          'projectTotalAmount': '9999元',
          'createTime': '2020-01-01 23:59:59'
        },
        {
          'projectId': 'JCUT-2020-GZM-02-01-3',
          'projectName': '这是一个特别长的系统设计与开发名称',
          'projectGroup': '欧阳娜娜，关晓彤，买瓶，付敏，江小白',
          'projectStatus': 0,
          'projectBudget': '2.00万元',
          'projectBalance': '1.0001万元',
          'projectTotalAmount': '9999元',
          'createTime': '2020-01-01 23:59:59'
        },
        {
          'projectId': 'JCUT-2020-GZM-02-01-0',
          'projectName': '这是一个特别长的系统设计与开发名称',
          'projectGroup': '欧阳娜娜，关晓彤，买瓶，付敏，江小白',
          'projectStatus': 0,
          'projectBudget': '2.00万元',
          'projectBalance': '1.0001万元',
          'projectTotalAmount': '9999元',
          'createTime': '2020-01-01 23:59:59'
        },
        {
          'projectId': 'JCUT-2020-GZM-02-01-1',
          'projectName': '这是一个特别长的系统设计与开发名称',
          'projectGroup': '欧阳娜娜，关晓彤，买瓶，付敏，江小白',
          'projectStatus': 0,
          'projectBudget': '2.00万元',
          'projectBalance': '1.0001万元',
          'projectTotalAmount': '9999元',
          'createTime': '2020-01-01 23:59:59'
        }
      ],
      da: {
        size: ''
      }
    }
  },
  created() {
    // eslint-disable-next-line eqeqeq   headers['X-Token']
    this.da.size = 0
    // this.query(this.pagec)
  },
  methods: {
    updateBudget() {
    },
    handleCurrentChange(val) {
      this.pagec = val
      this.query()
    },
    query() {
      // eslint-disable-next-line no-empty
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
      return row.projectStatus === 2 ? '待审核' : row.projectStatus === 1 ? '未结项' : row.projectStatus === 0 ? '已结项' : '待结项'
    },
    isVisibleFun(row, column) {
      return row.projectBalance === 0 ? '教师' : '学生'
    },
    tableRowClassName({ row, rowIndex }) {
      if (row.projectStatus === 0) {
        return 'white-row'
      } else if (row.projectStatus === 1) {
        return 'success-row'
      } else if (row.projectStatus === 2) {
        return 'warning-row'
      }
      return 'simple-row'
    },
    handleEdit(index, row) {
      this.showText = ''
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
      this.showText = '此操作将申请项目结算, 是否继续?'
      this.$confirm(this.showText, '风险提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.openNum = 1
        // 调接口
      }).catch(() => {
        this.openNum = 0
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
    },
    openMove(index, row) {
      this.dialogFormVisible = true
    },
    moveconfign() {
      this.dialogFormVisible = false
      this.showText = '此操作将不可恢复的转移项目预算, 是否继续？'
      this.$confirm(this.showText, '风险提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.openNum = 1
        // 调接口
      }).catch(() => {
        this.openNum = 0
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
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
  .el-table .white-row {
    background: #d3eeff;
  }
  .el-table .simple-row {
    background: #f9e2e7;
  }
</style>

<template>
  <div>
    <div style="margin-left: 5%; margin-bottom: 2%;margin-top: 2%;">
      <el-form>
        项目编号：<el-input style="width:200px;margin-right: 5%;" clearable v-model="projectl.projectId" autocomplete="off" placeholder="请输入项目编号" />
        项目名称：<el-input style="width:200px;margin-right: 5%;" clearable v-model="projectl.projectName" autocomplete="off" placeholder="请输入项目名称" />
        项目状态：<el-select @change="query" style="width:200px;" v-model="projectl.projectStatus" placeholder="请选择">
        <el-option label="已结项" value="0" />
        <el-option label="未结项" value="1" />
        <el-option label="待审核" value="2" />
      </el-select>
        <br /><el-button style="float:right;margin-right: 5%;" type="primary" @click="query">查询</el-button>
      </el-form>
    </div>
    <el-table
      border="true"
      :data="tableData"
      style="width: 90%; margin-left: 5%"
      :row-class-name="tableRowClassName">
      <el-table-column
        fixed="left"
        align="center"
        prop="projectId"
        show-overflow-tooltip="true"
        label="项目编号"
        width="215">
      </el-table-column>
      <el-table-column
        fixed="left"
        align="center"
        prop="projectName"
        show-overflow-tooltip="true"
        label="项目名称"
        width="180">
      </el-table-column>
      <el-table-column
        align="center"
        prop="groupName"
        show-overflow-tooltip="true"
        label="项目成员"
        width="180">
      </el-table-column>
      <el-table-column
        align="center"
        :formatter="cStatus"
        prop="projectStatus"
        width="100"
        label="项目状态">
      </el-table-column>
      <el-table-column
        align="center"
        width="150"
        prop="projectBudgetStr"
        @click="updateBudget"
        label="项目预算">
      </el-table-column>
      <el-table-column
        align="center"
        width="150"
        prop="projectBalanceStr"
        label="项目余额">
      </el-table-column>
      <el-table-column
        align="center"
        width="150"
        prop="projectTotalAmountStr"
        label="已用额度">
      </el-table-column>
      <el-table-column
        width="160"
        align="center"
        prop="createTime"
        label="立项时间">
      </el-table-column>
      <el-table-column label="操作" fixed="right" width="265" :formatter="isVisibleFun">
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.projectStatus==1 && scope.row.relatStatus == 0"
            size="mini"
            type="primary"
            @click="open(scope.$index, scope.row)">申请结项
          </el-button>
          <!--   || scope.row.projectStatus==0
            @click="open(scope.$index, scope.row)">{{scope.row.projectStatus==1?'申请结项':'结算清单'}}
-->
          <el-button
            v-if="scope.row.projectStatus==1 || scope.row.projectStatus==3 || scope.row.projectStatus==0"
            size="mini"
            type="warning"
            @click="jumpLink(scope.$index, scope.row)">经费
          </el-button>
          <!--v-if="scope.row.projectStatus==1 && scope.row.relatStatus == 0"   预算转移的条件-->
          <el-button
            v-if="scope.row.projectStatus==1 && scope.row.relatStatus == 0"
            size="mini"
            type="danger"
            @click="openMove(scope.$index, scope.row)">预算转移
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
import user from "../../../store/modules/user";
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
      allBanlance: 0,
      userId: '',
      projectId: '',
      pwd: '',
      stat: 1,
      tableData: [],
      da: {
        size: ''
      }
    }
  },
  created() {
    this.da.size = 0
    this.query(this.pagec)
  },
  methods: {
    jumpLink(index, row){
      let turl = this.$router.resolve({path: '../fundDetail/index',
        query:{
          p1: row.projectId,//localStorage.getItem("uid")
          p2: row.projectName,//localStorage.getItem("uid")
          p3: row.projectStatus===0?2:1
        }});
      window.open(turl.href, '_blank');
    },
    updateBudget() {},
    handleCurrentChange(val) {
      this.pagec = val
      this.query()
    },
    query() {
      // eslint-disable-next-line no-empty
      if (this.stat === this.projectl.projectStatus) {
      } else {
        this.stat = this.projectl.projectStatus
        this.pagec = 1
      }
      this.tableData = []
      const data = this.projectl
      data.pageSize = 10
      data.toDoMark = 500
      data.userId = user.state.userId
      data.pageCount = this.pagec
      axios.post(this.url + '/project/query', data, {
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
      return row.projectStatus === 2 ? '待立项审核' : row.projectStatus === 1 ? '未结项' : row.projectStatus === 0 ? '已结项' : '待结项审核'
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
    open(index, row) {
      this.showText = '此操作将申请结项, 是否继续?'
      this.$confirm(this.showText, '风险提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.openNum = 1
        const data = { 'userId': row.userId, 'projectId': row.projectId }
        data.desc = 3
        axios.post(this.url + '/project/update', data, {
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
      }).catch(() => {
        this.openNum = 0
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
    },
    openMove(index, row) {
      this.moveform.projectBanalce = row.tempBanlance/100
      this.allBanlance = row.tempBanlance/100
      this.userId = row.userId
      this.projectId = row.projectId
      this.dialogFormVisible = true
    },
    moveconfign() {
      if(this.moveform.projectId == '' || this.moveform.projectId == null){
        this.$message.error('项目编号不能为空')
        return
      }
      if(this.allBanlance*100 < this.moveform.projectBanalce*100){
        this.$message.error('余额不足')
        return
      }
      this.dialogFormVisible = false
      const data = { 'commitUserId': this.userId,'commitMain': user.state.name, 'projectId': this.projectId }
      data.desc = 2
      data.moveBanlance = (this.moveform.projectBanalce*100).toString()
      data.moveProjectId = this.moveform.projectId
      axios.post(this.url + '/project/detail/move', data, {
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
            this.query(this.pagec)
          }
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

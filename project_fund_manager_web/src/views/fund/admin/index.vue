<template>
  <div>
    <div style="margin-left: 5%; margin-bottom: 2%;margin-top: 2%;">
      <el-form>
        项目编号：<el-input style="width:200px;margin-right: 5%;" clearable v-model="projectl.projectId" autocomplete="off" placeholder="请输入项目编号" />
        项目名称：<el-input style="width:200px;margin-right: 5%;" clearable v-model="projectl.projectName" autocomplete="off" placeholder="请输入项目名称" />
        项目状态：<el-select @change="query" style="width:200px;" v-model="projectl.toDoMark" placeholder="请选择">
        <el-option label="待审核" value="1" />
        <el-option label="无审核" value="0" />
        <el-option label="全部" value="500" />
      </el-select>
        <br /><br />
        <el-tag>已用资产：{{ b1 }}</el-tag>
        <el-tag>剩余资产：{{ b2 }}</el-tag>
        <el-button style="float:right;margin-right: 5%;" type="primary" @click="query">查询</el-button>
      </el-form>
    </div>
    <el-table
      border="true"
      :data="tableData"
      @cell-dblclick="jump"
      style="width: 90%; margin-left: 5%"
      :row-class-name="tableRowClassName">
      <el-table-column
        fixed="left"
        align="center"
        show-overflow-tooltip="true"
        prop="projectId"
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
      <!--@click="openpwd(scope.$index, scope.row)">{{scope.row.projectStatus===0?'隐藏':(scope.row.projectStatus===1?'结项':(scope.row.projectStatus===2?'通过':'通过'))}}
          @click="open(scope.$index, scope.row)">{{scope.row.projectStatus===0?'隐藏':(scope.row.projectStatus===1?'隐藏':(scope.row.projectStatus===2?'不通过':'不通过'))}}
-->
      <el-table-column label="操作" fixed="right" width="250" :formatter="isVisibleFun">
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.toDoMark==1 && scope.row.applicatType ==1 && (scope.row.projectStatus==2 || scope.row.projectStatus==3)"
            size="mini"
            type="primary"
            @click="openpwd(scope.$index, scope.row)">通过
          </el-button>
          <el-button
            v-if="scope.row.projectStatus==9"
            size="mini"
            type="primary"
            @click="opendia(scope.$index, scope.row)">结算清单
          </el-button>

          <el-button
            v-if="scope.row.toDoMark==1 && scope.row.applicatType ==1 && (scope.row.projectStatus==2 || scope.row.projectStatus==3)"
            size="mini"
            type="danger"
            @click="open(scope.$index, scope.row)">退回
          </el-button>
          <el-button
            v-if="scope.row.applicatType==0 && scope.row.projectStatus!=0"
            size="mini"
            type="primary"
            @click="openpwd(scope.$index, scope.row)">{{scope.row.projectStatus==1?'结项':'通过'}}
          </el-button>
          <el-button
            v-if="scope.row.applicatType==0 && scope.row.projectStatus==2"
            size="mini"
            type="danger"
            @click="open(scope.$index, scope.row)">退回
          </el-button>
          <el-button
            v-if="scope.row.projectStatus==1 || scope.row.projectStatus==3 || scope.row.projectStatus==0"
            size="mini"
            type="warning"
            @click="jumpLink(scope.$index, scope.row)">经费
          </el-button>

          <el-button
            v-if="scope.row.whereBalance == 1 && scope.row.applicatType==0 && scope.row.projectStatus!=2"
            size="mini"
            type="danger"
            @click="budget(scope.$index, scope.row)">预算转移
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
    <el-dialog title="结项清单" :visible.sync="dialogTableVisible" center>
      <el-table :data="gridData">
        <el-table-column property="date" label="日期" width="150"></el-table-column>
        <el-table-column property="name" label="姓名" width="200"></el-table-column>
        <el-table-column property="address" label="地址"></el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogTableVisible = false">确 定</el-button>
      </div>
    </el-dialog>
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
    <!--<el-container>
      <el-main>
        <div style="display: inline;float: right; width: 50%; border: #3b4151 solid 1px">
          <ve-pie :data="chartData" height="400px"></ve-pie>
        </div>
      </el-main>
    </el-container>-->
  </div>
</template>

<script>
import user from '../../../store/modules/user'
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
      gridData: [],
      dialogTableVisible: false,
      isVisible: 'none', // 按钮隐藏   :style="{ display: isVisible }"
      projectl: {
        projectId: '',
        projectName: '',
        projectStatus: '500',
        toDoMark: '1'
      },
      showStatus: 0,
      pagec: 1,
      allBanlance: 0,
      showText: '',
      b1: '',
      b2: '',
      openNum: 0,
      banlance: '',
      stat: 1,
      tableData: [],
      da: {
        size: ''
      },
      th:0,
      userId:'',
      projectId:''
    }
  },
  created() {
    this.da.size = 0
    this.all()
    this.query(this.pagec)
  },
  methods: {
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
    },
    budget(index, row){
      this.moveform.projectBanalce = row.tempBanlance/100
      this.allBanlance = row.tempBanlance/100
      this.userId = row.userId
      this.projectId = row.projectId
      this.dialogFormVisible = true
    },
    all() {
      let nn = new Date().toDateString()
      const data = {}
      data.thisYear = nn.substring(nn.length-4, nn.length)
      axios.post(this.url + '/project/cost/query1', data, {
        headers: { 'X-Token': getToken() }
      })
        .then(res => {
          if (res.data.code === 20000) {
            this.b1 = res.data.data[0].descr
            this.b2 = res.data.data[1].descr
            return
            /*this.$message({
              message: res.data.desc,
              type: 'success'
            })*/
          } else {
            this.$message.error(res.data.desc)
            return
          }
        })
      return
    },
    jumpLink(index, row){
      let turl = this.$router.resolve({path: '../fundDetail/index',
        query:{
          p1: row.projectId,//localStorage.getItem("uid")
          p2: row.projectName,//localStorage.getItem("uid")
          p3: row.projectStatus===0?2:1//localStorage.getItem("uid")
        }});
      window.open(turl.href, '_blank');
    },
    jump(row){
      //双击事件
    },
    opendia(index, row) {
      this.dialogTableVisible = true
    },
    updateBudget() {},
    handleCurrentChange(val) {
      this.pagec = val
      this.query()
    },
    query() {
      if (this.stat === this.projectl.toDoMark) {
      } else {
        this.stat = this.projectl.toDoMark
        this.pagec = 1
      }
      this.tableData = []
      const data = this.projectl
      data.pageSize = 10
      data.userId = user.state.userId
      data.pageCount = this.pagec
      data.projectStatus = 500
      axios.post(this.url + '/project/queryPage', data, {
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
      return row.projectStatus === 2 ? '立项审核' : row.projectStatus === 1 ? '未结项' : row.projectStatus === 0 ? '已结项' : '结项审核'
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
      const data = { 'userId': row.userId, 'projectId': row.projectId }
      //2--1 立项通过  11 立项不通过         3---0结项通过   10 结项不通过
      //scope.row.toDoMark==1 &&
      // scope.row.applicatType ==1 &&
      // (scope.row.projectStatus==2 || scope.row.projectStatus==3)"
      if (row.projectStatus === 2) {
        data.desc = 1
        data.projectBudget = this.banlance
      } else if (row.projectStatus === 3 && this.th === 0) {
        data.desc = 0
      } else if (row.projectStatus === 3 && this.th === 1) {
        data.desc = 10
      } else if (row.projectStatus === 1 && row.applicatType==0) {
        data.desc = 0
      } else {
        data.desc = 500
      }
      if (this.openNum === 1) {
        this.openNum = 0
        axios.post(this.url + '/project/update', data, {
          headers: { 'X-Token': getToken() }
        })
          .then(res => {
            if (res.data.code === 20000) {
              this.th = 0
              this.$message({
                message: res.data.desc,
                type: 'success'
              })
              if(data.desc === 1){
                this.all()
              }
              this.query(this.pagec)
            } else {
              this.th = 0
              this.$message.error(res.data.desc)
            }
          })
      }
    },
    handleDelete(index, row) {
      //2--1 立项通过  11 立项不通过
      //scope.row.toDoMark==1 &&
      // scope.row.applicatType ==1 &&
      // (scope.row.projectStatus==2 || scope.row.projectStatus==3)"
      const data = { 'userId': row.userId, 'projectId': row.projectId }
      let uurl = ''
      if (row.projectStatus === 2) {
        uurl = '/project/deleteById'
        data.desc = 11
      } else {}
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
      if (row.projectStatus === 2) {
        this.showText = '此操作将退回项目的立项审核, 是否继续?'
      } else if (row.projectStatus === 3) {
        this.showText = '此操作将退回项目的结项审核, 是否继续?'
      } else {}
      this.$confirm(this.showText, '风险提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.openNum = 1
        if(row.projectStatus === 2){
          this.handleDelete(index, row)
        }else if(row.projectStatus === 3){
          this.th = 1
          this.handleEdit(index, row)
        }else{}
      }).catch(() => {
        this.openNum = 0
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
    },
    //scope.row.toDoMark==1 &&
    // scope.row.applicatType ==1 &&
    // (scope.row.projectStatus==2 || scope.row.projectStatus==3)"
    openpwd(index, row) {
      if (row.projectStatus === 2) {
        this.showText = '此操作将通过项目的立项审核, 是否继续?'
      } else if (row.projectStatus === 3) {
        this.showText = '此操作将通过项目的结项审核, 是否继续?'
      } else if (row.projectStatus === 1 && row.applicatType==0) {
        this.showText = '此操作将通过项目的结项审核, 是否继续?'
      } else {}
      if (row.projectStatus === 2) {
        this.$prompt('请输入批复额度', '确认金额', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          inputPlaceholder: row.projectBudgetStr,
          inputValue:row.projectBudgetStr.toString().substring(0, row.projectBudgetStr.toString().length-1)
        }).then(({ value }) => {
          if((this.b2*100 < value*100) && row.whereBalance==1){
            this.openNum = 0
            this.$message.error('剩余资产不足')
            return
          }
          this.banlance = value
          this.openNum = 1
          this.handleEdit(index, row)
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
          this.handleEdit(index, row)
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
}
</script>

<style scoped>
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
  .el-header {
    background-color: #1F5DEA;
    color: white;
    text-align: center;
    line-height: 60px;
    font-size: 24px;
  }
  .data-empty {
    position: absolute;
    left: 0;
    right: 0;
    top: 0;
    bottom: 0;
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: rgba(255, 255, 255, .7);
    color: #888;
    font-size: 14px;
  }
</style>

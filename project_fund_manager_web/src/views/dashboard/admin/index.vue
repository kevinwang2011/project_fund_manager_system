<!--<template>
  <div>
    <div>
      <div style="margin-left: 5%; margin-bottom: 2%;margin-top: 2%;">
        <p style="font-size: 13pt">待办事项：</p>
        &lt;!&ndash;<el-form>
          项目编号：<el-input style="width:200px;margin-right: 5%;" clearable v-model="projectl.projectId" autocomplete="off" placeholder="请输入项目编号" />
          项目名称：<el-input style="width:200px;margin-right: 5%;" clearable v-model="projectl.projectName" autocomplete="off" placeholder="请输入项目名称" />
          <el-button style="margin-right: 5%;" type="primary" @click="query">查询</el-button>
        </el-form>&ndash;&gt;
      </div>
      <el-table
        :data="tableData"
        style="width: 90%; margin-left: 5%"
        :row-class-name="tableRowClassName">
        <el-table-column
          align="center"
          prop="acceptanceId"
          label="受理编号"
          width="180">
        </el-table-column>
        <el-table-column
          align="center"
          prop="projectId"
          show-overflow-tooltip="true"
          width="270"
          label="项目编号">
        </el-table-column>
        <el-table-column
          align="center"
          prop="projectName"
          show-overflow-tooltip="true"
          label="项目名称">
        </el-table-column>
        <el-table-column
          align="center"
          width="150"
          prop="projectMain"
          label="负责人">
        </el-table-column>
        &lt;!&ndash;<el-table-column
          align="center"
          width="150"
          prop="toDoThing"
          label="待办事项">
        </el-table-column>&ndash;&gt;
        <el-table-column
          align="center"
          width="150"
          :formatter="cStatus"
          prop="thingType"
          label="待办类别">
          &lt;!&ndash;<template slot-scope="{row}">
            <el-tag :type="row.thingType">
              {{ row.thingType }}
            </el-tag>
          </template>&ndash;&gt;
        </el-table-column>
        <el-table-column
          width="153"
          align="center"
          prop="commitTime"
          label="提交时间">
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
    </div>
  </div>
</template>-->
<template>
  <div class="dashboard-editor-container">
    <span style="font-size: 13pt">项目申请</span><br><br><br>
    <div style="margin-left: 25%; width: 50%;text-align: center;">
      <el-form :model="registerPro" :rules="registerProRules">
        <el-form-item label="项目编号" :label-width="formLabelWidth" prop="projectId">
          <el-input v-model="registerPro.projectId" clearable autocomplete="off" placeholder="请输入项目编号" />
        </el-form-item>
        <el-form-item label="项目名称" :label-width="formLabelWidth" prop="projectName">
          <el-input v-model="registerPro.projectName" clearable autocomplete="off" placeholder="请输入项目名称" />
        </el-form-item>
        <el-form-item label="项目类别" :label-width="formLabelWidth">
          <el-select v-model="registerPro.projectType" style="width: 100%" placeholder="请选择">
            <el-option label="大创项目" value="0" />
            <el-option label="团队项目" value="1" /></el-select>
        </el-form-item>
        <el-form-item label="项目预算" :label-width="formLabelWidth" prop="projectBudget">
          <el-input v-model="registerPro.projectBudget" clearable autocomplete="off" placeholder="请输入项目预算" />
        </el-form-item>
        <el-form-item label="资产统计" :label-width="formLabelWidth" prop="projectNode">
          <!--<el-input-number v-model="registerPro.projectNode" style="width: 100%" :min="1"
                           :max="10" size="medium" />-->
          <el-radio-group style="float: left" v-model="registerPro.projectNode">
            <el-radio :label="1" border>计入资产</el-radio>
            <el-radio :label="500" border>不计入资产</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="负  责  人" :label-width="formLabelWidth">
          <el-input v-model="registerPro.projectMain" readonly autocomplete="off" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="项目成员" :label-width="formLabelWidth">
          <el-input v-model="registerPro.projectGroup" clearable autocomplete="off" placeholder="请输入项目成员的账户编号，以英文逗号隔开" />
        </el-form-item>
      </el-form>
      <div>
        <el-button type="warning" @click="clearForm">清空</el-button>
        <el-button type="primary" @click="commit">提交</el-button>
      </div>
    </div>
  </div>
</template>
<script>
import user from '../../../store/modules/user'
import axios from 'axios'
import { getToken } from '@/utils/auth'

export default {
  name: 'DashboardEditor',
  components: {},
  data() {
    return {
      formLabelWidth: '130px',
      registerPro: {
        projectId: '',
        projectType: '1',
        userId: user.state.userId,
        projectName: '',
        projectGroup: '',
        projectBudget: '',
        projectNode: 1,
        projectMain: user.state.name
      },
      registerProRules: {
        projectId: [{ required: true, message: '项目编号不能为空', trigger: 'blur' }],
        projectType: [{ required: true, message: '项目类别不能为空', trigger: 'blur' }],
        projectName: [{ required: true, message: '项目名称不能为空', trigger: 'blur' }],
        projectNode: [{ required: true, message: '请选择计入类型', trigger: 'blur' }],
        projectBudget: [{ required: true, message: '项目预算不能为空', trigger: 'blur' }]
      }
    }
  },
  computed: {},
  methods: {
    clearForm() {
      this.registerPro.projectMain = user.state.name
      this.registerPro.userId = user.state.userId
      this.registerPro.projectType = '1'
      this.registerPro.projectId = ''
      this.registerPro.projectName = ''
      this.registerPro.projectGroup = ''
      this.registerPro.projectBudget = ''
      this.registerPro.projectNode = ''
    },
    commit() {
      if(this.registerPro.projectBudget <= 0){
        this.$message.error('请正确输入项目预算！')
        return
      }
      var data = this.registerPro
      data.applicatType = 0
      data.toDoMark = 1
      if (data.projectBudget === '') {
        this.$message.error('请输入项目预算！')
        return
      } else if (data.projectMain === '') {
      } else if (data.projectType === '') {
      } else if (data.projectId === '') {
        this.$message.error('请输入项目编号！')
        return
      } else if (data.projectName === '') {
        this.$message.error('请输入项目名称！')
        return
      } else if (data.projectNode < 0) {
        this.$message.error('请选择计入类型！')
        return
      } else {
        axios.post(this.url + '/project/add', data, {
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
    }
  }
}
</script>

<style lang="scss" scoped>
  .emptyGif {
    display: block;
    width: 45%;
    margin: 0 auto;
  }

  .dashboard-editor-container {
    background-color: #e3e3e3;
    min-height: 100vh;
    padding: 50px 60px 0px;
    .pan-info-roles {
      font-size: 12px;
      font-weight: 700;
      color: #333;
      display: block;
    }
    .info-container {
      position: relative;
      margin-left: 190px;
      height: 150px;
      line-height: 200px;
      .display_name {
        font-size: 48px;
        line-height: 48px;
        color: #212121;
        position: absolute;
        top: 25px;
      }
    }
  }
</style>

<!--<script>
import axios from 'axios'
import { getToken } from '@/utils/auth'
export default {
  name: 'DashboardAdmin',
  components: {
  },
  data() {
    return {
      formLabelWidth: '100px',
      projectl: {
        projectId: '',
        projectName: '',
        projectStatus: '1'
      },
      showStatus: 0,
      pagec: 1,
      stat: 1,
      tableData: [
        {
          'acceptanceId': '15235623223112',
          'projectName': '这是一个特别长的系统设计与开发名称',
          'projectMain': '欧阳娜娜',
          'projectId': 'JCUT-2020-GZM-02-01-0',
          'toDoThing': '2.00万元',
          'thingType': 0,
          'commitTime': '2020-01-01 23:59:59'
        },
        {
          'acceptanceId': '15235623223113',
          'projectName': '这是一个特别长的系统设计与开发名称',
          'projectMain': '欧阳娜娜',
          'projectId': 'JCUT-2020-GZM-02-01-1',
          'toDoThing': '2.00万元',
          'thingType': 2,
          'commitTime': '2020-01-01 23:59:59'
        },
        {
          'acceptanceId': '15235623223123',
          'projectName': '这是一个特别长的系统设计与开发名称',
          'projectId': 'JCUT-2020-GZM-02-01-2',
          'projectMain': '关晓彤',
          'toDoThing': '2.00万元',
          'thingType': 4,
          'commitTime': '2020-01-01 23:59:59'
        },
        {
          'acceptanceId': '15235623293112',
          'projectName': '这是一个特别长的系统设计与开发名称',
          'projectId': 'JCUT-2020-GZM-02-01-3',
          'projectMain': '关晓彤',
          'toDoThing': '2.00万元',
          'thingType': 5,
          'commitTime': '2020-01-01 23:59:59'
        },
        {
          'acceptanceId': '15235629965223',
          'projectName': '这是一个特别长的系统设计与开发名称',
          'projectId': 'JCUT-2020-GZM-02-01-0',
          'projectMain': '欧阳娜娜',
          'toDoThing': '2.00万元',
          'thingType': 6,
          'commitTime': '2020-01-01 23:59:59'
        },
        {
          'acceptanceId': '15235625236987',
          'projectName': '这是一个特别长的系统设计与开发名称',
          'projectId': 'JCUT-2020-GZM-02-01-1',
          'projectMain': '关晓彤',
          'toDoThing': '2.00万元',
          'thingType': 7,
          'commitTime': '2020-01-01 23:59:59'
        },
        {
          'acceptanceId': '15235625236989',
          'projectName': '这是一个特别长的系统设计与开发名称',
          'projectId': 'JCUT-2020-GZM-02-01-7',
          'projectMain': '关晓彤',
          'toDoThing': '2.00万元',
          'thingType': 8,
          'commitTime': '2020-01-01 23:59:59'
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
    handleCurrentChange(val) {
      this.pagec = val
      this.query()
    },
    cStatus(row, column) {
      if (row.thingType === 0) {
        row.thingType = '结项'
      } else if (row.thingType === 2) {
        row.thingType = '开题'
      } else if (row.thingType === 4) {
        row.thingType = '经费申请'
      } else if (row.thingType === 5) {
        row.thingType = '经费核销'
      } else if (row.thingType === 6) {
        row.thingType = '项目预结算'
      } else if (row.thingType === 7) {
        row.thingType = '项目结算'
      } else if (row.thingType === 8) {
        row.thingType = '转移预算'
      }
      return row.thingType
    },
    tableRowClassName({ row, rowIndex }) {
      if (rowIndex % 2 === 0) {
        return 'warning-row'
      }
      //  'success-row'   'warning-row'  'simple-row'   'white-row'
      return 'success-row'
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
    }
  }
}
</script>

<style lang="scss" scoped>
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
.dashboard-editor-container {
  padding: 32px;
  background-color: rgb(240, 242, 245);
  position: relative;

  .github-corner {
    position: absolute;
    top: 0px;
    border: 0;
    right: 0;
  }

  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 32px;
  }
}

@media (max-width:1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}
</style>-->

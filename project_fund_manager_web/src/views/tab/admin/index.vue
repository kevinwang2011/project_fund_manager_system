<template>
  <div class="tab-container">
    <div style="margin-bottom: 2%;margin-top: 2%;">
      <el-form>
        <!--项目编号：<el-input v-model="queryg.projectId" style="width:300px;margin-right: 5%;"
                       clearable autocomplete="off" placeholder="请输入项目编号" />-->
        项目名称：<el-autocomplete style="width:300px;margin-right: 5%;" v-model="state"
        :fetch-suggestions="querySearchAsync" placeholder="请输入项目名称" @select="handleSelect" clearable></el-autocomplete>
        <!--<el-input v-model="queryg.projectName" style="width:200px;margin-right: 5%;"
                  clearable autocomplete="off" placeholder="请输入项目名称" />-->
        <!--项目状态：<el-select style="width:200px;" v-model="queryg.userStatus" placeholder="请选择">
        <el-option label="已停用" value="0" />
        <el-option label="待审核" value="1" />
        <el-option label="已通过" value="2" /></el-select>-->

        <!--<el-button style="" type="primary" @click="query">查询</el-button>-->
      </el-form>
    </div>
    <el-tag>项目编号：{{ projectId }}</el-tag><br><br>
    <el-tag>项目名称：{{ projectName }}</el-tag>
    <!--
    <el-alert :closable="false" style="width:200px;display:inline-block;vertical-align: middle;margin-left:30px;" title="Tab with keep-alive" type="success" />
-->
    <el-tabs v-model="activeName" type="border-card" style="margin-top: 5px" @tab-click="handleClick">
      <el-tab-pane name="detail" label="经费明细">
        <el-table :data="dataList" border fit highlight-current-row style="width: 100%">
          <el-table-column
            v-loading="loading"
            align="center"
            label="科目编号"
            width="150"
            element-loading-text="请给我点时间！">
            <template slot-scope="scope">
              <span>{{ scope.row.fundId }}</span>
            </template>
          </el-table-column>
          <el-table-column width="250" align="center" prop="fundName" :formatter="checkp" label="科目名称">
            <!--<template slot-scope="scope">
              <span>{{ scope.row.fundName }}</span>
            </template>-->
          </el-table-column>
          <el-table-column width="165" show-overflow-tooltip="true" align="center" label="科目摘要">
            <template slot-scope="scope">
              <span>{{ scope.row.fundDesc }}</span>
            </template>
          </el-table-column>
          <el-table-column width="130px" align="center" label="项目支出">
            <template slot-scope="scope">
              <span>{{ scope.row.fundAmountOutStr }}</span>
            </template>
          </el-table-column>
          <el-table-column width="130px" align="center" label="项目收入">
            <template slot-scope="scope">
              <span>{{ scope.row.fundAmountInStr }}</span>
            </template>
          </el-table-column>
          <el-table-column width="130px" align="center" label="项目余额">
            <template slot-scope="scope">
              <span>{{ scope.row.fundBalanceStr }}</span>
            </template>
          </el-table-column>
          <el-table-column align="center" label="提交人">
            <template slot-scope="scope">
              <span>{{ scope.row.commitMain }}</span>
            </template>
          </el-table-column>
          <el-table-column width="180" align="center" label="提交时间">
            <template slot-scope="scope">
              <span>{{ scope.row.commitTime }}</span>
            </template>
          </el-table-column>
          <el-table-column width="110" align="center" label="预计使用时间">
            <template slot-scope="scope">
              <span>{{ scope.row.futureTime }}</span>
            </template>
          </el-table-column>
          <el-table-column class-name="status-col" label="科目状态" width="110">
            <template slot-scope="{row}">
              <el-tag :type="row.fundStatus">
                {{ row.fundStatus==1?'待通过':row.fundStatus==2?'待预结算':row.fundStatus==3?'待核销': row.fundStatus==5?'待结算':row.fundStatus==6?'已结算':'无'}}
              </el-tag>
            </template>
          </el-table-column>
        </el-table>
        <div style="margin-top: 2%;text-align: center">
          <el-pagination
            background
            :current-page="this.pagec"
            layout="prev, pager, next"
            :page-size="10"
            :total="dataSize"
            @current-change="handleCurrentChange"
          />
        </div>
      </el-tab-pane>
      <el-tab-pane name="application" label="经费申请">
        <div style="margin-left: 25%; width: 50%;text-align: center;">
          <el-form :model="applicatForm" :rules="applicatFormRules">
            <el-form-item label="项目编号" :label-width="formLabelWidth" prop="projectId">
              <el-input v-model="applicatForm.projectId" readonly autocomplete="off" placeholder="请输入项目编号" />
            </el-form-item>
            <el-form-item label="项目名称" :label-width="formLabelWidth" prop="projectName">
              <el-input v-model="applicatForm.projectName" readonly autocomplete="off" placeholder="请输入项目名称" />
            </el-form-item>
            <el-form-item label="经费科目" :label-width="formLabelWidth" prop="fundName">
              <el-select v-model="applicatForm.fundName" style="width: 100%" placeholder="请选择">
                <el-option label="设备费-设备购置费" value="1" />
                <el-option label="设备费-设备试制费" value="2" />
                <el-option label="设备费-设备升级改造与租赁费" value="3" />
                <el-option label="材料费" value="4" />
                <el-option label="测试化验加工费" value="5" />
                <el-option label="燃料动力费" value="6" />
                <el-option label="差旅/会议/国际合作与交流费" value="7" />
                <el-option label="出版/文献/信息传播/知识产权事务费" value="8" />
                <el-option label="劳务费" value="9" />
                <el-option label="专家咨询费" value="10" />
                <el-option label="其他支出" value="11" />
              </el-select>
            </el-form-item>
            <el-form-item label="使用时间" :label-width="formLabelWidth" prop="futureTime">
              <el-date-picker
                v-model="applicatForm.futureTime"
                style="width: 100%"
                align="right"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="选择预计使用时间"
                :picker-options="pickerOptions"
              />
            </el-form-item>
            <el-form-item label="科目金额" :label-width="formLabelWidth" prop="fundAmountOut">
              <el-input v-model="applicatForm.fundAmountOut" clearable autocomplete="off" placeholder="请输入科目金额" />
            </el-form-item>
            <el-form-item label="申  请  人" :label-width="formLabelWidth">
              <el-input v-model="applicatForm.commitMain" readonly autocomplete="off" placeholder="请输入姓名" />
            </el-form-item>
            <el-form-item label="科目摘要" :label-width="formLabelWidth" prop="fundDesc">
              <el-input v-model="applicatForm.fundDesc" clearable autocomplete="off" placeholder="请描述经费用途" />
            </el-form-item>
          </el-form>
          <div>
            <el-button type="warning" @click="p22">清空</el-button>
            <el-button v-if="commitStatus != '待审核' && commitStatus != '' && commitStatus != null" type="primary" @click="p2">提交</el-button>
          </div>
        </div>
      </el-tab-pane>
      <el-tab-pane name="applicat-form" label="经费审核">
        <el-table :data="dataList" border fit highlight-current-row style="width: 100%">
          <el-table-column
            v-loading="loading"
            align="center"
            label="科目编号"
            width="150"
            element-loading-text="请给我点时间！"
          >
            <template slot-scope="scope">
              <span>{{ scope.row.fundId }}</span>
            </template>
          </el-table-column>
          <el-table-column width="269" prop="fundName" :formatter="checkp" align="center" label="科目名称">
          </el-table-column>
          <el-table-column width="165" align="center" show-overflow-tooltip="true" label="科目摘要">
            <template slot-scope="scope">
              <span>{{ scope.row.fundDesc }}</span>
            </template>
          </el-table-column>
          <el-table-column width="130px" align="center" label="科目金额">
            <template slot-scope="scope">
              <span>{{ scope.row.fundAmountOutStr }}</span>
            </template>
          </el-table-column>
          <el-table-column align="center" label="提交人">
            <template slot-scope="scope">
              <span>{{ scope.row.commitMain }}</span>
            </template>
          </el-table-column>
          <el-table-column width="155px" align="center" label="提交时间">
            <template slot-scope="scope">
              <span>{{ scope.row.commitTime }}</span>
            </template>
          </el-table-column>
          <el-table-column class-name="status-col" label="科目状态" width="100">
            <template slot-scope="{row}">
              <el-tag :type="row.fundStatus">
                {{ row.fundStatus==1?'待通过':row.fundStatus==2?'待预结算':row.fundStatus==3?'待核销': row.fundStatus==5?'待结算':row.fundStatus==6?'已结算':'无'}}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column class-name="status-col" label="操作" width="160">
            <template slot-scope="{row}">
              <el-button v-if="row.fundStatus!=4 && row.fundType != 5" size="mini" type="primary" @click="open(row,1,1)">通过</el-button>
              <el-button v-if="row.fundStatus!=4 && row.fundType != 5" size="mini" type="warning" @click="open(row,2,1)">不通过</el-button>
              <el-button v-if="row.fundStatus!=4 && row.fundType == 5" size="mini" type="primary" @click="open1(row,1)">通过</el-button>
              <el-button v-if="row.fundStatus!=4 && row.fundType == 5" size="mini" type="warning" @click="open1(row,0)">不通过</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div style="margin-top: 2%;text-align: center">
          <el-pagination
            background
            :current-page="this.pagec"
            layout="prev, pager, next"
            :page-size="10"
            :total="dataSize"
            @current-change="handleCurrentChange"
          />
        </div>
      </el-tab-pane>
      <el-tab-pane name="in-advance" label="经费预结算">
        <el-table :data="dataList" border fit highlight-current-row style="width: 100%">
          <el-table-column
            v-loading="loading"
            align="center"
            label="科目编号"
            width="150"
            element-loading-text="请给我点时间！"
          >
            <template slot-scope="scope">
              <span>{{ scope.row.fundId }}</span>
            </template>
          </el-table-column>
          <el-table-column width="269" prop="fundName" :formatter="checkp" align="center" label="科目名称">
          </el-table-column>
          <el-table-column width="165" align="center" show-overflow-tooltip="true" label="科目摘要">
            <template slot-scope="scope">
              <span>{{ scope.row.fundDesc }}</span>
            </template>
          </el-table-column>
          <el-table-column width="130px" align="center" label="科目金额">
            <template slot-scope="scope">
              <span>{{ scope.row.fundAmountOutStr }}</span>
            </template>
          </el-table-column>
          <el-table-column align="center" label="提交人">
            <template slot-scope="scope">
              <span>{{ scope.row.commitMain }}</span>
            </template>
          </el-table-column>
          <el-table-column width="155px" align="center" label="提交时间">
            <template slot-scope="scope">
              <span>{{ scope.row.commitTime }}</span>
            </template>
          </el-table-column>
          <el-table-column class-name="status-col" label="科目状态" width="100">
            <template slot-scope="{row}">
              <el-tag :type="row.fundStatus">
                {{ row.fundStatus==1?'待通过':row.fundStatus==2?'待预结算':row.fundStatus==3?'待核销': row.fundStatus==5?'待结算':row.fundStatus==6?'已结算':'无'}}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column class-name="status-col" label="操作" width="160">
            <template slot-scope="{row}">
              <el-button v-if="row.fundStatus!=4" size="mini" type="primary" @click="open(row,1,2)">通过</el-button>
              <el-button v-if="row.fundStatus!=4" size="mini" type="warning" @click="open(row,2,2)">不通过</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div style="margin-top: 2%;text-align: center">
          <el-pagination
            background
            :current-page="this.pagec"
            layout="prev, pager, next"
            :page-size="10"
            :total="dataSize"
            @current-change="handleCurrentChange"
          />
        </div>
      </el-tab-pane>
      <el-tab-pane name="write-off" label="经费核销">
        <el-table :data="dataList" border fit highlight-current-row style="width: 100%">
          <el-table-column
            v-loading="loading"
            align="center"
            label="科目编号"
            width="150"
            element-loading-text="请给我点时间！"
          >
            <template slot-scope="scope">
              <span>{{ scope.row.fundId }}</span>
            </template>
          </el-table-column>
          <el-table-column width="269" prop="fundName" :formatter="checkp" align="center" label="科目名称">
          </el-table-column>
          <el-table-column width="165" align="center" show-overflow-tooltip="true" label="科目摘要">
            <template slot-scope="scope">
              <span>{{ scope.row.fundDesc }}</span>
            </template>
          </el-table-column>
          <el-table-column width="130px" align="center" label="科目金额">
            <template slot-scope="scope">
              <span>{{ scope.row.fundAmountOutStr }}</span>
            </template>
          </el-table-column>
          <el-table-column align="center" label="提交人">
            <template slot-scope="scope">
              <span>{{ scope.row.commitMain }}</span>
            </template>
          </el-table-column>
          <el-table-column width="155px" align="center" label="提交时间">
            <template slot-scope="scope">
              <span>{{ scope.row.commitTime }}</span>
            </template>
          </el-table-column>
          <el-table-column class-name="status-col" label="科目状态" width="100">
            <template slot-scope="{row}">
              <el-tag :type="row.fundStatus">
                {{ row.fundStatus==1?'待通过':row.fundStatus==2?'待预结算':row.fundStatus==3?'待核销': row.fundStatus==5?'待结算':row.fundStatus==6?'已结算':'无'}}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column class-name="status-col" label="操作" width="160">
            <template slot-scope="{row}">
              <el-button v-if="row.fundStatus!=4" size="mini" type="primary" @click="open(row,1,3)">通过</el-button>
              <el-button v-if="row.fundStatus!=4" size="mini" type="warning" @click="open(row,2,3)">不通过</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div style="margin-top: 2%;text-align: center">
          <el-pagination
            background
            :current-page="this.pagec"
            layout="prev, pager, next"
            :page-size="10"
            :total="dataSize"
            @current-change="handleCurrentChange"
          />
        </div>
      </el-tab-pane>
    </el-tabs>
    <!--<el-tabs v-model="activeName" style="margin-top:15px;" type="border-card">
      <el-tab-pane v-for="item in tabMapOptions" :key="item.key" :label="item.label" :name="item.key">
        <keep-alive>
          <tab-pane v-if="activeName==item.key" :type="item.key" @click="showCreatedTimes" />
        </keep-alive>
      </el-tab-pane>
    </el-tabs>-->
  </div>
</template>

<script>
//  import tabPane from './components/TabPane'
import { getToken } from '@/utils/auth'
import user from '../../../store/modules/user'
import axios from "axios";

export default {
  name: 'Tab',
  //  components: { tabPane },
  data() {
    return {
      restaurants: [],
      restaurants2: [],
      state: '',
      timeout:  null,
      relatNum: 0,
      fundType: '',
      queryg: {
        'projectId': '',
        'projectName': '',
        'userId': ''
      },
      applicatForm: {
        'projectId': '',
        'projectName': '',
        'fundName': '',
        'fundAmountOut': '',
        'futureTime': '',
        'commitMain': user.state.name,
        'commitUserId': user.state.userId,
        'fundDesc': ''
      },
      openNum:'',
      banlance:'',
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() < Date.now() - 3600 * 1000 * 24 * 28
        },
        shortcuts: [{
          text: '今天',
          onClick(picker) {
            picker.$emit('pick', new Date())
          }
        }, {
          text: '明天',
          onClick(picker) {
            const date = new Date()
            date.setTime(date.getTime() + 3600 * 1000 * 24)
            picker.$emit('pick', date)
          }
        }, {
          text: '一周后',
          onClick(picker) {
            const date = new Date()
            date.setTime(date.getTime() + 3600 * 1000 * 24 * 7)
            picker.$emit('pick', date)
          }
        }]
      },
      pagec: 1,
      dataSize: 0,
      dataList: [],
      openNum:0,
      tempName: '',
      tabMapOptions: [
        { label: '经费明细', key: 'detail' },
        { label: '经费核销', key: 'write-off' },
        { label: '经费申请', key: 'application' },
        { label: '经费预结算', key: 'in-advance' }
      ],
      formLabelWidth: '130px',
      activeName: 'detail',
      commitStatus:'',
      projectName: '',
      projectId: '',
      userId: '',
      applicatFormRules: {
        projectId: [{ required: true, message: '项目编号不能为空', trigger: 'blur' }],
        fundName: [{ required: true, message: '科目类别不能为空', trigger: 'blur' }],
        projectName: [{ required: true, message: '项目名称不能为空', trigger: 'blur' }],
        fundAmountOut: [{ required: true, min: 0, message: '科目金额不能为空', trigger: 'blur' }],
        futureTime: [{ required: true, message: '预计使用时间不能为空', trigger: 'blur' }],
        fundDesc: [{ required: true, message: '科目摘要不能为空', trigger: 'blur' }]
      }
    }
  },
  watch: {
    activeName(val) {
      this.$router.push(`${this.$route.path}?tab=${val}`)
    }
  },
  created() {
    const tab = this.$route.query.tab
    if (tab) {
      this.activeName = tab
    }
    this.applicatForm.commitMain = user.state.name
    this.userId = user.state.userId
    this.queryg.userId = this.userId
    this.projectId = this.$route.query.p1
    this.projectName = this.$route.query.p2
    this.fundType = this.$route.query.p3
    this.commitStatus = this.$route.query.p3
    this.queryg.projectId = this.projectId
    this.queryg.projectName = this.projectName
    this.state = this.queryg.projectName
    this.handleClick()
  },
  mounted() {
    this.loadAll()
  },
  methods: {
    open1(row, val) {
      if (val === 1) {
        this.showText = '此操作将通过项目的转移预算审核, 是否继续?'
      } else if (val === 0) {
        this.showText = '此操作将退回项目的转移预算审核, 是否继续?'
      } else {}
      this.$confirm(this.showText, '风险提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.openNum = 1
        this.openMove(row, val)
      }).catch(() => {
        this.openNum = 0
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
    },
    openMove(row,val) {
      this.openNum = 0
      const data = { 'commitUserId': this.userId,'commitMain': user.state.name, 'projectId': row.projectId, 'fundId': row.fundId }
      data.desc = val
      axios.post(this.url + '/project/detail/moveInfo', data, {
        headers: { 'X-Token': getToken() }
      })
        .then(res => {
          if (res.data.code === 20000) {
            this.$message({
              message: res.data.desc,
              type: 'success'
            })
            this.query(1)
          } else {
            this.$message.error(res.data.desc)
            this.query(1)
          }
        })
    },
    checkp(row, column){
      return row.fundName === 1 ? '设备费-设备购置费' : row.fundName === 2 ? '设备费-设备试制费' :
        row.fundName === 3 ? '设备费-设备升级改造与租赁费':row.fundName === 4 ? '材料费'
          :row.fundName === 5 ? '测试化验加工费' :row.fundName === 6 ? '燃料动力费' :
          row.fundName === 7 ? '差旅/会议/国际合作与交流费' :row.fundName === 8 ? '出版/文献/信息传播/知识产权事务费'
            :row.fundName === 9 ? '劳务费':row.fundName === 10 ? '专家咨询费':row.fundName === 11 ?'其他支出':''
    },
    handleClick(tab, event) {
      if(this.projectId == '' || this.projectName == ''){
        this.$message.error('请先选择项目')
        return
      }else if(this.state == '' || this.state == null){
        this.$message.error('请先选择项目')
        return
      }else{}
      if(this.tempName === this.activeName){
      }else{
        this.pagec=1
        this.dataList = []
        this.dataSize = 0
      }
      if (this.activeName === 'detail') {
        //alert('调明细接口')
        this.tempName = this.activeName
        this.query(0)
      } else if (this.activeName === 'write-off') {
        //alert('调核销接口')
        this.tempName = this.activeName
        this.query(3)
      } else if (this.activeName === 'in-advance') {
        //alert('调预结算接口')
        this.tempName = this.activeName
        this.query(2)
      } else if (this.activeName === 'applicat-form') {
        //alert('调审核接口')
        this.tempName = this.activeName
        this.query(1)
      } else if (this.activeName === 'application') {
        //申请
        this.tempName = this.activeName
        this.applicatForm.projectId = this.projectId
        this.applicatForm.projectName = this.projectName
      } else {
        //alert('啥也不干')
      }
      // console.log(tab, event);queryPage
    },
    query(val) {
      const data = { 'userId':this.userId,'projectId':this.projectId,'desc':val}
      data.pageSize = 10
      data.pageCount = this.pagec
      axios.post(this.url + '/project/detail/queryPage', data, {
        headers: {'X-Token': getToken()}
      })
        .then(res => {
          if (res.data.code === 20000) {
            this.dataList = res.data.data
            this.dataSize = res.data.totalSize
            this.$message({
              message: res.data.desc,
              type: 'success'
            })
          } else {
            this.$message.error(res.data.desc)
          }
        })
    },
    loadAll() {
      const data ={}
      axios.post(this.url + '/project/queryList', data, {
        headers: { 'X-Token': getToken() }
      })
        .then(res => {
          if (res.data.code === 20000) {
            this.restaurants = res.data.data
          } else {}
        })
    },
    relat(index, row) {
      this.userId = row.userId
      this.userName = row.userName
      this.relatNum = 1
    },
    querySearchAsync(queryString, cb) {
      var restaurants = this.restaurants
      var results = queryString ? restaurants.filter(this.createStateFilter(queryString)) : restaurants
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
    handleSelect(item) {
      this.projectId = item.projectId
      this.projectName = item.value.substring(0, item.value.indexOf('('))
      this.commitStatus = item.value.substring(item.value.indexOf('(')+1, item.value.indexOf(')'))
      this.queryg.projectId = this.projectId
      this.queryg.projectName = this.projectName
      this.applicatForm.projectId = this.projectId
      this.applicatForm.projectName = this.projectName
      this.handleClick()
    },
    showCreatedTimes() {
    },
    handleCurrentChange(val) {
      this.pagec = val
      this.handleClick()
    },
    p22(){
      this.applicatForm.fundAmountOut=''
      this.applicatForm.fundDesc=''
      this.applicatForm.fundName=''
      this.applicatForm.futureTime=''
    },
    p2(){
      const data = this.applicatForm
      data.userId = this.userId
      if(this.applicatForm.fundAmountOut <= 0){
        this.$message.error('请正确输入科目金额!')
        return
      }
      axios.post(this.url + '/project/detail/applicat', data, {
        headers: {'X-Token': getToken()}
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

    },
    update(desc, val, pid, fid,stat){
      const data = { 'fundId':fid,'projectId':pid,'desc':val}
      this.openNum = 0
      let uurl = ''
      if(val > 0){
        uurl = '/project/detail/update'
      }else{
        uurl ='/project/detail/delete'
      }
      if(stat ===1){
        data.askStatus = 1
        data.fundBalance = this.banlance
      }else{
        data.askStatus = 2
      }
      axios.post(this.url + uurl, data, {
        headers: {'X-Token': getToken()}
      })
        .then(res => {
          if (res.data.code === 20000) {
            this.$message({
              message: res.data.desc,
              type: 'success'
            })
            this.query(desc)
          } else {
            this.$message.error(res.data.desc)
          }
        })
    },
    open(row,val,typ) {
      if (val === 1) {
        this.$prompt('请输入批复额度', '确认金额', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          inputPlaceholder: row.fundAmountOutStr,
          inputValue:row.fundAmountOutStr.toString().substring(0, row.fundAmountOutStr.toString().length-1)
        }).then(({ value }) => {
          this.banlance = value
          this.openNum = 1
          if(typ === 1){
            this.update(1,5,row.projectId,row.fundId,1)
          }else if(typ === 2){
            this.update(2,6,row.projectId,row.fundId,1)
          }else if(typ === 3){
            this.update(3,6,row.projectId,row.fundId,1)
          }else {}
        }).catch(() => {
          this.openNum = 0
          this.$message({
            type: 'info',
            message: '取消输入'
          })
        })
      } else if (val === 2) {
        this.$confirm('该操作将退回此次申请，是否继续', '退回提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.openNum = 1
          if(typ === 1){
            this.update(1,0,row.projectId,row.fundId,2)
          }else if(typ === 2){
            this.update(2,5,row.projectId,row.fundId,2)
          }else if(typ === 3){
            this.update(3,5,row.projectId,row.fundId,2)
          }else {}
        }).catch(() => {
          this.openNum = 0
          this.$message({
            type: 'info',
            message: '已取消'
          })
        })
      } else {}
    }
  }
}
</script>

<style scoped>
  .tab-container {
    margin: 30px;
  }
</style>

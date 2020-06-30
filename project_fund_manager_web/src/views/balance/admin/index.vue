<template>
    <div>
      <el-row :gutter="16" body-style="">
        <el-col :span="8" style="height: 100%">
          <el-card shadow="never">
            <div style="text-align: center"><br />资产确认</div><br /><br /><br />
            <hr style="color:#fffef9"/><br /><br /><br /><br />
            <el-form :label-position="posit" label-width="80px" :model="formLabel">
              <el-form-item label="年份">
                <el-date-picker style="width: 100%"
                                v-model="formLabel.thisYear"
                                align="center"
                                type="year"
                                value-format="yyyy"
                                default-value=""
                                placeholder="选择年">
                </el-date-picker>
              </el-form-item>
              <el-form-item label="总经费">
                <el-input clearable v-model="formLabel.allBalance"></el-input>
              </el-form-item>
              <div style="text-align: center"><br /><el-button @click="commit" type="primary">确定</el-button></div>
            </el-form><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br />
          </el-card>
        </el-col>
        <el-col :span="8">
          <el-card shadow="never">
            <div style="text-align: center">资产明细</div><br />
            <el-form :label-position="posit" label-width="80px">
              <el-form-item label="年份">
                <el-date-picker style="width: 100%"
                                v-model="form.thisYear"
                                align="center"
                                type="year"
                                value-format="yyyy"
                                @change="all"
                                default-value=""
                                placeholder="选择年">
                </el-date-picker>
              </el-form-item>
            </el-form>
            <hr style="color:#fffef9"/><br />
            <ve-pie :data="chartData" :settings="chartSettings" :extend="colorconf"><div v-if="this.flag1 === 1" style="text-align: center;margin-top: -50%;" class="data-empty">没有数据😂</div></ve-pie>
            <div v-if="this.form.thisYear != ''" style="text-align: center"><br /><el-button @click="all" type="primary">同步</el-button></div>
          </el-card>
        </el-col>
        <el-col :span="8">
          <el-card shadow="never">
            <div style="text-align: center">项目明细</div><br />
            <el-form :label-position="posit" label-width="80px">
              <el-form-item label="项目名称">
                <el-autocomplete style="width:100%" v-model="state"
                                 :fetch-suggestions="querySearchAsync"
                                 placeholder="请输入项目名称" @select="handleSelect"
                                 clearable></el-autocomplete>
              </el-form-item>
            </el-form>
            <hr style="color:#fffef9"/><br />
            <ve-pie :data="chartData1" :settings="chartSettings1" :extend="colorconf"><div v-if="this.flag2 === 1"  style="text-align: center;margin-top: -50%;" class="data-empty">没有数据😂</div></ve-pie>
            <div v-if="this.projectId != ''" style="text-align: center"><br /><el-button @click="query2" type="primary">同步</el-button></div>
          </el-card>
        </el-col>
      </el-row>
    </div>
</template>

<script>
import user from "../../../store/modules/user";
import axios from "axios";
import { getToken } from '@/utils/auth'
export default {
  name: "index",
  data(){
    return {
      colorconf: {
        color: ["#ef5b9c", "#7fb80e", "#4ad2ff","#FFD700", "#4f91f1"]
      },
      chartSettings: {
        level: [
          ['已用资产', '剩余资产'],
          ['总资产']
        ]
      },
      chartSettings1: {
        level: [
          ['已分配资产', '未分配资产'],
          ['已用资产', '剩余资产'],
          ['总资产']
        ]
      },
      chartData: {
        columns: ['total', 'descr'],
        rows: [
          //{'total': '已用资产', 'descr': 1393},
          //{'total': '未用资产', 'descr': 3530},
          //{'total': '总资产', 'descr': 2923},
          //{ 'a': '已用资产', 'b': 15000},
          //{ 'a': '未用资产', 'b': 5000}
          //{ 'a': '总资产', 'b': '已用资产', 'c': '未用资产'},
        ]
      },
      chartData1: {
        columns: ['total', 'descr'],
        rows: [
          //{'total': '已分配资产', 'descr': 1393},
          //{'total': '未分配资产', 'descr': 3530},
          //{'total': '总资产', 'descr': 2923},
          //{ 'a': '已用资产', 'b': 15000},
          //{ 'a': '未用资产', 'b': 5000}
          //{ 'a': '总资产', 'b': '已用资产', 'c': '未用资产'},
        ]
      },
      posit:'left',
      formLabel:{
        allBalance:'',
        thisYear:new Date()
      },
      form:{
        thisYear:new Date()
      },
      restaurants: [],
      restaurants2: [],
      state: '',
      timeout:  null,
      flag1: 0,
      flag2: 0,
      relatNum: 0,
      thisYear:'',
      commitStatus: '',
      projectName: '',
      projectId: ''
    }
  },
  created() {
    let nn = new Date().toDateString()
    this.thisYear = nn.substring(nn.length-4, nn.length)
    this.formLabel.thisYear = this.thisYear
    this.form.thisYear = this.thisYear
    this.all()
  },
  mounted() {
    this.loadAll()
  },
  methods: {
    query2() {
      const data = {}
      data.projectId = this.projectId
      axios.post(this.url + '/project/cost/query2', data, {
        headers: { 'X-Token': getToken() }
      })
        .then(res => {
          if (res.data.code === 20000) {
            this.chartData1.rows = res.data.data
            this.$message({
              message: res.data.desc,
              type: 'success'
            })
          } else {
            this.chartData1.rows ={}
            this.$message.error(res.data.desc)
          }
        })
    },
    commit(){
      if(this.formLabel.thisYear ==='' ||this.formLabel.thisYear === null){
        this.$message.error('年份不能为空')
        return
      }
      if(this.formLabel.allBalance ==='' ||this.formLabel.allBalance === null ||this.formLabel.allBalance*100 < 0){
        this.$message.error('资产不能为空或小于0')
        return
      }
      const data = {}
      data.thisYear = this.formLabel.thisYear
      data.allBalance = this.formLabel.allBalance
      axios.post(this.url + '/project/cost/addYear', data, {
        headers: { 'X-Token': getToken() }
      })
        .then(res => {
          if (res.data.code === 20000) {
            this.$message({
              message: res.data.desc,
              type: 'success'
            })
            this.form.thisYear = data.thisYear
            this.all()
          } else {
            this.$message.error(res.data.desc)
          }
        })
    },
    all() {
      if(this.form.thisYear ==='' ||this.form.thisYear === null){
        this.$message.error('年份不能为空')
        return
      }
      const data = {}
      data.thisYear = this.form.thisYear
      axios.post(this.url + '/project/cost/query1', data, {
        headers: { 'X-Token': getToken() }
      })
        .then(res => {
          if (res.data.code === 20000) {
            this.chartData.rows = res.data.data
            this.formLabel.allBalance = res.data.allBalance.toString()
            this.$message({
              message: res.data.desc,
              type: 'success'
            })
          } else {
            this.chartData.rows =res.data.data
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
      this.query2()
    }
  }
}
</script>

<style scoped>

</style>

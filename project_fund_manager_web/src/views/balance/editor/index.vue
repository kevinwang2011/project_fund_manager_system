<template>
  <div>
    <el-row :gutter="16" body-style="">
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
          <ve-pie :data="chartData1" :settings="chartSettings1" :extend="colorconf"><div v-if="this.flag === 1" style="text-align: center;margin-top: -50%;" class="data-empty">没有数据😂</div></ve-pie>
          <div v-if="this.projectId != ''" style="text-align: center"><br /><el-button @click="query" type="primary">同步</el-button></div>
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
          color: ["#ef5b9c", "#7fb80e", "#4ad2ff", "#FFD700", "#4f91f1"]
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
            //{'total': '已用资产', 'desc': 1393},
            //{'total': '未用资产', 'desc': 3530},
            //{'total': '总资产', 'desc': 2923},
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
        columns: ['total', 'descr'],
        posit:'left',
        formLabel:{
          allBalance:'',
          thisYear:''
        },
        restaurants: [],
        restaurants2: [],
        state: '',
        flag: 0,
        timeout:  null,
        relatNum: 0,
        commitStatus: '',
        projectName: '',
        projectId: ''
      }
    },
    created() {

    },
    mounted() {
      this.loadAll()
    },
    methods: {
      query() {
        /*if(this.commitStatus ==='待审核'){
          this.flag = 1
          this.chartData1={}
          return
        }else{
          this.chartData1.columns = this.columns
          this.flag = 0
        }*/
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
              this.$message.error(res.data.desc)
            }
          })
      },
      loadAll() {
        const data ={'userId': user.state.userId, 'desc': 2}
        axios.post(this.url + '/projectRelat/queryList', data, {
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
        this.query()
      }
    }
  }
</script>

<style scoped>

</style>

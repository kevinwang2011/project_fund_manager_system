<template>
  <div>
  <el-table :data="list" border fit highlight-current-row style="width: 100%">
    <el-table-column
      v-loading="loading"
      align="center"
      label="科目编号"
      width="300"
      element-loading-text="请给我点时间！"
    >
      <template slot-scope="scope">
        <span>{{ scope.row.projectId }}</span>
      </template>
    </el-table-column>
    <el-table-column width="269" align="center" label="科目名称">
      <template slot-scope="scope">
        <span>{{ scope.row.fundType }}</span>
      </template>
    </el-table-column>
    <!--<el-table-column  align="center" label="科目摘要">
      <template slot-scope="scope">
        <span>{{ scope.row.projectName }}</span>
      </template>
    </el-table-column>-->
    <!--<el-table-column min-width="300px" label="Title">
      <template slot-scope="{row}">
        <span>{{ row.title }}</span>
        <el-tag>{{ row.type }}</el-tag>
      </template>
    </el-table-column>-->
    <el-table-column width="150px" align="center" label="科目金额">
      <template slot-scope="scope">
        <span>{{ scope.row.fundAmount }}</span>
      </template>
    </el-table-column>
    <el-table-column  align="center" label="负责人">
      <template slot-scope="scope">
        <span>{{ scope.row.projectMain }}</span>
      </template>
    </el-table-column>
    <el-table-column width="155px" align="center" label="提交时间">
      <template slot-scope="scope">
        <span>{{ scope.row.commitTime }}</span>
      </template>
    </el-table-column>
    <!--<el-table-column width="120px" label="Importance">
      <template slot-scope="scope">
        <svg-icon v-for="n in +scope.row.importance" :key="n" icon-class="star" />
      </template>
    </el-table-column>-->

   <!-- <el-table-column align="center" label="Readings" width="95">
      <template slot-scope="scope">
        <span>{{ scope.row.pageviews }}</span>
      </template>
    </el-table-column>-->

    <el-table-column class-name="status-col" label="科目状态" width="100">
      <template slot-scope="{row}">
        <el-tag :type="row.projectStatus">
          {{ row.projectStatus }}
        </el-tag>
      </template>
    </el-table-column>
    <el-table-column class-name="status-col" label="操作" width="160">
      <template slot-scope="{row}">
        <el-button size="mini" type="primary">通过</el-button>
        <el-button size="mini" type="warning">退回</el-button>
      </template>
    </el-table-column>
  </el-table>
  <div style="margin-top: 2%;text-align: center">
    <el-pagination
      background
      @current-change="handleCurrentChange"
      :current-page="this.pagec"
      layout="prev, pager, next"
      :page-size="5"
      :total=dataSize>
    </el-pagination>
  </div>
  </div>
</template>

<script>

export default {
  filters: {
    statusFilter(status) {
      const statusMap = {
        published: 'success',
        draft: 'info',
        deleted: 'danger'
      }
      return statusMap[status]
    }
  },
  props: {
    type: {
      type: String,
      default: 'detail'
    }
  },
  data() {
    return {
      pagec: 1,
      dataSize: 12,
      dataList: [],
      items: [
        { 'projectId': '2020-01-02MZXdgfd123456', 'projectName': '项目经费管理系统设计与开发', 'projectMain': '张三',
          'commitTime': '2020-01-02 12:12:12', 'fundType': '出版/文献/信息传播/知识产权事务费',
          'fundAmount': '1222.01元', 'projectStatus': '待通过' },
        { 'projectId': '2020-01-02MZXdgfd123', 'projectName': '项目经费管理系统', 'projectMain': '欧阳娜娜',
          'commitTime': '2020-01-02 12:12:12', 'fundType': '设备购置费',
          'fundAmount': '1222.01元', 'projectStatus': '待通过' },
        { 'projectId': '2020-01-02MZXdgfd123', 'projectName': '项目经费管理系统设计与开发', 'projectMain': '张三',
          'commitTime': '2020-01-30 23:59:59', 'fundType': '设备购置费',
          'fundAmount': '1222.01元', 'projectStatus': '待结算' },
        { 'projectId': 'JCUT-2020-GZM-02-01', 'projectName': '项目经费管理系统', 'projectMain': '唐古拉四',
          'commitTime': '2020-12-30 23:59:59', 'fundType': '设备购置费',
          'fundAmount': '1222.01万元', 'projectStatus': '待通过' },
        { 'projectId': '2020-01-02MZXdgfd123', 'projectName': '项目经费管理系统', 'projectMain': '张三',
          'commitTime': '2020-01-02 12:12:12', 'fundType': '设备购置费',
          'fundAmount': '1222.01元', 'projectStatus': '待核销' }
      ],
      list: null,
      listQuery: {
        page: 1,
        limit: 10,
        type: this.type,
        sort: '+id'
      },
      loading: false
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      // this.$emit('create') // for test
      this.list = this.items
      this.loading = false
      if (this.$route.query.tab === 'detail') {
        alert('调明细接口')
      } else if (this.$route.query.tab === 'write-off') {
        alert('调核销接口')
      } else if (this.$route.query.tab === 'in-advance') {
        alert('调预结算接口')
      } else {
        alert('啥也不干')
      }
      /*  fetchList(this.listQuery).then(response => {
        this.list = response.data.items
        this.loading = false
      })  */
    },
    handleCurrentChange() {
    }
  }
}
</script>


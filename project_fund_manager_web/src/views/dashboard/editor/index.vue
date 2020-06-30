<template>
  <div class="dashboard-editor-container">
    <span style="font-size: 13pt">项目申请</span><br /><br /><br />
    <div style="margin-left: 25%; width: 50%;text-align: center;">
      <el-form :model="registerPro" :rules="registerProRules">
        <el-form-item label="项目编号" :label-width="formLabelWidth" prop="projectId">
          <el-input v-model="registerPro.projectId" clearable autocomplete="off" placeholder="请输入项目编号" />
        </el-form-item>
        <el-form-item label="项目名称" :label-width="formLabelWidth" prop="projectName">
          <el-input v-model="registerPro.projectName" clearable  autocomplete="off" placeholder="请输入项目名称" />
        </el-form-item>
        <el-form-item label="项目类别" :label-width="formLabelWidth">
          <el-select style="width: 100%" v-model="registerPro.projectType" placeholder="请选择">
            <el-option label="大创项目" value="0" />
            <el-option label="团队项目" value="1" /></el-select>
        </el-form-item>
        <el-form-item label="项目预算" :label-width="formLabelWidth" prop="projectBudget">
          <el-input  v-model="registerPro.projectBudget" clearable autocomplete="off" placeholder="请输入项目预算" />
        </el-form-item>
        <el-form-item label="项目节点" :label-width="formLabelWidth" prop="projectNode">
          <el-input-number style="width: 100%" :min="1" :max="10" size="medium" v-model="registerPro.projectNode"></el-input-number>
        </el-form-item>
        <el-form-item label="负  责  人" :label-width="formLabelWidth">
          <el-input v-model="registerPro.projectMain" readonly autocomplete="off" placeholder="请输入姓名" ></el-input>
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
  components: { },
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
        projectNode: '',
        projectMain: user.state.name
      },
      registerProRules: {
        projectId: [{ required: true, message: '项目编号不能为空', trigger: 'blur' }],
        projectType: [{ required: true, message: '项目类别不能为空', trigger: 'blur' }],
        projectName: [{ required: true, message: '项目名称不能为空', trigger: 'blur' }],
        projectBudget: [{ required: true, message: '项目预算不能为空', trigger: 'blur' }]
      }
    }
  },
  computed: {
  },
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
      var data = this.registerPro
      data.applicatType = 1
      data.toDoMark = 1
      if (data.projectBudget === '') {
      } else if (data.projectMain === '') {
      } else if (data.projectType === '') {
      } else if (data.projectId === '') {
      } else if (data.projectName === '') {
      } else if (data.projectNode === '') {
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

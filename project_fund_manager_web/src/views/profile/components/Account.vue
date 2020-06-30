<template>
  <el-form>
   <!-- <el-form-item label="账户">
      <el-input v-model.trim="userId" />
    </el-form-item>-->
    <el-form-item style="margin: 3% 0" label="密码">
      <el-input style="width: 80%"
        placeholder="请输入密码"
        v-model="userr.pwd"
        clearable>
      </el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submit">确定</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import user from '../../../store/modules/user'
import axios from 'axios'
import { getToken } from '@/utils/auth'
export default {
  data(){
    return{
      userr:{
        pwd:''
      }
    }
  },
  props: {
    user: {
      type: Object,
      default: () => {
        return {
          userId: user.state.userId
        }
      }
    }
  },
  methods: {
    submit() {
      if(this.userr.pwd == null || this.userr.pwd ==''){
        this.$message.error('密码不能为空')
        return
      }
      const data ={'userPwd':this.userr.pwd, 'userId':user.state.userId}
      axios.post(this.url + '/user/updateId', data, {
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
    }
  }
}
</script>

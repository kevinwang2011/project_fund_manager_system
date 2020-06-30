<template>
  <div class="dashboard-container">
    <component :is="currentRole" />
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import admin3 from './admin'
import editor3 from './editor/index'

export default {
  name: '',
  components: { admin3, editor3 },
  data() {
    return {
      currentRole: 'admin3'
    }
  },
  computed: {
    ...mapGetters([
      'roles'
    ])
  },
  created() {
    if (!this.roles.includes('admin')) {
      this.currentRole = 'editor3'
    }
  }
}
</script>

<template>
  <div class="dashboard-container">
    <component :is="currentRole" />
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import admin1 from './admin'
import editor2 from './editor'

export default {
  name: '',
  components: { admin1, editor2 },
  data() {
    return {
      currentRole: 'admin1'
    }
  },
  computed: {
    ...mapGetters([
      'roles'
    ])
  },
  created() {
    if (!this.roles.includes('admin')) {
      this.currentRole = 'editor2'
    }
  }
}
</script>

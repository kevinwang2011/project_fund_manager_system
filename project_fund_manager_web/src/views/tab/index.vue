<template>
  <div class="dashboard-container">
    <component :is="currentRole" />
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import adminfund from './admin'
import editorfund from './editor'

export default {
  name: '',
  components: { adminfund, editorfund },
  data() {
    return {
      currentRole: 'adminfund'
    }
  },
  computed: {
    ...mapGetters([
      'roles'
    ])
  },
  created() {
    if (!this.roles.includes('admin')) {
      this.currentRole = 'editorfund'
    }
  }
}
</script>

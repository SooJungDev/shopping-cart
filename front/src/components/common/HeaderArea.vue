<template>
  <v-toolbar app fixed clipped-left>
    <v-toolbar-side-icon @click.stop="toggleSideMenuDrawer"></v-toolbar-side-icon>
    <v-toolbar-title>Wallet 관리</v-toolbar-title>
    <v-spacer></v-spacer>
    <v-toolbar-items class="hidden-sm-and-down">
      <v-btn flat>My page</v-btn>
      <v-btn flat @click="logOut"><v-icon>power_settings_new</v-icon>Log out</v-btn>
    </v-toolbar-items>
  </v-toolbar>
</template>

<script>
import { mapMutations, mapActions } from 'vuex';

export default {
  name: 'HeaderArea',
  methods: {
    ...mapActions([
      'logout',
    ]),
    ...mapMutations([
      'toggleSideMenuDrawer',
      'setShowCommonMenu',
    ]),
    logOut() {
      this.$router.push('/');
      this.logout();
    },
  },
};
</script>

<style scoped>

</style>

<template>
  <v-content>
    <v-container fluid fill-height>
      <v-layout align-center justify-center>
        <v-flex xs12 sm8 md4>
          <v-card class="elevation-12">
            <v-toolbar  color="#00C1A3">
              <v-toolbar-title>shopping cart</v-toolbar-title>
              <v-spacer></v-spacer>
            </v-toolbar>
            <v-card-text>
              <v-form>
               <v-text-field  name="login"
                              label="Login" type="text" v-model="username">

                </v-text-field>
                 <v-text-field id="password" name="password"
                              label="Password" type="password" v-model="password"
                              @keyup.enter.native="clickLogin">

                </v-text-field>
              </v-form>
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="#00C1A3" @click="clickLogin">Login</v-btn>
            </v-card-actions>
          </v-card>
        </v-flex>
      </v-layout>
    </v-container>
  </v-content>
</template>
<script>
import { mapMutations, mapActions } from 'vuex'

export default {
  name: 'login',
  components: {
  },
  created () {
    // 이벤트 등록
    // 초기화 코드 실행
  },
  props: {
  },
  data () {
    return {
      username: '',
      password: ''
    }
  },
  computed: {
  },
  methods: {
    ...mapMutations([
      'setShowCommonMenu'
    ]),
    ...mapActions([
      'login'
    ]),
    clickLogin () {
      const username = this.username.trim()
      const password = this.password.trim()

      if (username === '') {
        alert('아이디를 입력해주세요')
        return
      }
      if (password === '') {
        alert('비밀번호를 입력해주세요.')
        return
      }

      const user = {
        username,
        password
      }
      this.login(user)
      this.initValue()
    },
    initValue () {
      this.username = ''
      this.password = ''
    }
  },
  destroyed () {
    // 등록된 이벤트 해지
    // 메모리 해지
  }
}
</script>

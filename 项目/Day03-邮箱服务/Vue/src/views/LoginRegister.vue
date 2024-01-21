<template>

  <div>
      <div v-if="isLogined == 'false' || isLogined == null">
        <h1>Case02-登陆注册</h1>
        <div :class="{'card': true, 'flipped': isFlipped}">
          <div class="card-login">
            <h2>登陆</h2>
            <div class="login-input">
              <el-input clearable v-model="use.login.uid" class="login-input-item" placeholder="请输入账号"></el-input>
              <el-input clearable show-password v-model="use.login.pwd" type="password" class="login-input-item" placeholder="请输入密码"></el-input>
              <el-input clearable v-model="use.login.yan" style="width: 55%;float: left;" class="login-input-item" placeholder="请输入验证码"></el-input>
              <img @click="generateCaptcha" style="margin-top: 24px;cursor: pointer;" class="login-input-item" :src="picUrl" alt="">
              <br>
              <span style="font-size: 7px;">看不清？点击图片换一张</span>
              <div style="position: relative;">
                <span style="position: fixed;left: 20%;margin-top: 20px;font-size: 11px;cursor: pointer;" @click="toggleFlip">还没有账号？去注册一个</span>
                <el-button @click="login_btn" style="display: block;margin-top: 20px;position: fixed;right: 20%;">登陆</el-button>
              </div>
            </div>
            
            <div style="position: relative;">
              <!-- <font-awesome-icon icon="message" style="position: fixed;bottom: 10px;font-size: 24px;"/>
              <font-awesome-icon icon="mailReply" style="position: fixed;bottom: 10px;font-size: 24px;"/> -->
              
            </div>
          </div>
          <div class="card-register">
            <h2>注册</h2>
            <div class="login-input">
              <el-input clearable v-model="use.register.uid" class="login-input-item" placeholder="请输入账号"></el-input>
              <el-input clearable show-password v-model="use.register.pwd" type="password" class="login-input-item" placeholder="请输入密码"></el-input>
              <el-input clearable show-password v-model="use.register.pwd2" type="password" class="login-input-item" placeholder="请确认密码"></el-input>
              <el-input clearable v-model="use.register.yan" style="width: 55%;float: left;" class="login-input-item" placeholder="请输入验证码"></el-input>
              <img @click="generateCaptcha" style="margin-top: 34px;cursor: pointer;" class="login-input-item" :src="picUrl" alt="">
              <br>
              <span style="font-size: 7px;">看不清？点击图片换一张</span>
              <div style="position: relative;">
                <el-button size="mini" style="position: fixed;left: 20%;margin-top: 20px;font-size: 11px;cursor: pointer;" @click="toggleFlip">返回</el-button>
                <el-button @click="register_btn" style="display: block;margin-top: 20px;position: fixed;right: 20%;">注册</el-button>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div v-else>
        <h2>欢迎{{ username }}</h2>
        <p>注册时间：{{ create_time }}</p>
        <span @click="logout" style="float: right;margin-right: 20px;cursor: pointer;padding: 1px;background-color: #e3e3e3;">登出</span>
      </div>
  </div>
</template>


<script>
export default {
  data() {
  return {
    isFlipped: false,
    captcha: '',
    picUrl: '',
    use: {
      login: {
        uid: '',
        pwd: '',
        yan: ''
      },
      register: {
        uid: '',
        pwd: '',
        pwd2: '',
        yan: ''
      }
    },
    username: '',
    create_time: '',
    isLogined: ''
  }
},
created() {
  this.isLogined = localStorage.getItem("isLogined")
  this.username = localStorage.getItem("username")
  this.create_time = localStorage.getItem("create_time")
},
mounted() {
  this.generateCaptcha()
},
methods: {
  logout() {
    localStorage.clear()
    this.$message({
          message: '退出成功',
          type: 'success'
        })
        setTimeout(function() {
          location.reload()
        }, 1500)
  },
  login_btn() {
    if(!this.use.login.uid) {
      this.$message({
          message: '亲 没写账号哦',
          type: 'warning'
      })
    } else if(!this.use.login.pwd) {
      this.$message({
          message: '密码是留给我的嘛',
          type: 'warning'
      })
    } else if(!this.use.login.yan) {
      this.$message({
          message: '嘿 还有验证码嘞',
          type: 'warning'
      })
    } else if(this.captcha !== this.use.login.yan) {
      this.$message({
          message: '验证码超明显的好不好',
          type: 'warning'
      })
      this.generateCaptcha()
      this.use.login.yan = ''
    } else {
      // console.log(this.use.login)
      const value = {
        nickname: this.use.login.uid,
        pwd: this.use.login.pwd
      }
      // console.log(value)
      const urlParams = new URLSearchParams();
      urlParams.append('nickname', value.nickname);
      urlParams.append('pwd', value.pwd);
      fetch(`http://124.221.138.245:40109/login?${urlParams}`, {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
      })
      .then(res => res.json())
      .then(res => {
        // console.log(res)
        localStorage.setItem("username", res.nickname)
        localStorage.setItem("create_time",res.create_time)
        localStorage.setItem("isLogined", true)
        this.$message({
          message: '登陆成功',
          type: 'success'
        })
        setTimeout(function() {
          location.reload()
        }, 3000)
      })
      .catch(res =>  {
        console.log(res)
        this.use.login.pwd = ''
        this.use.login.yan = ''
        this.generateCaptcha()
        this.$message({
          message: '密码或账号错误',
          type: 'warning'
        })
      })
    }
  },
  register_btn() {
      if(!this.use.register.uid) {
      this.$message({
          message: '亲 没写账号哦',
          type: 'warning'
      })
    } else if(!this.use.register.pwd) {
      this.$message({
          message: '密码是留给我的嘛',
          type: 'warning'
      })
    } else if(!this.use.register.pwd2) {
      this.$message({
          message: '你的密码不会这就忘了吧',
          type: 'warning'
      })
    } else if(!this.use.register.yan) {
      this.$message({
          message: '嘿 还有验证码嘞',
          type: 'warning'
      })
    } else if(this.captcha !== this.use.register.yan) {
      this.$message({
          message: '验证码超明显的好不好',
          type: 'warning'
      })
      this.generateCaptcha()
      this.use.register.yan = ''
    } else if(this.use.register.pwd !== this.use.register.pwd2) {
      this.$message({
          message: '密码这就忘啦？',
          type: 'warning'
      })
      this.use.register.pwd2 = ''
      this.use.register.yan = ''
      this.generateCaptcha()
    } else {
      const value = {
        nickname: this.use.register.uid,
        pwd: this.use.register.pwd
      }
      fetch("http://124.221.138.245:40109/register", {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(value)
      })
      this.$message({
        message: '注册成功',
        type: 'success'
      })
      this.use.register = ''
      setTimeout(function() {
        location.reload()
      }, 1500)
    }
  //   console.log(this.use.register)
  },
  toggleFlip() {
    this.isFlipped = !this.isFlipped;
  },
  generateCaptcha() {
    const img_w = 90;
    const img_h = 30;
    const char_len = 4;
    const font = '25px Arial';

    const char = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789'.split('');
    let code = '';
    for (let i = 0; i < char_len; i++) {
      code += char[Math.floor(Math.random() * char.length)];
    }
    // console.log(code)
    const canvas = document.createElement('canvas');
    canvas.width = img_w;
    canvas.height = img_h;
    const ctx = canvas.getContext('2d');

    ctx.fillStyle = 'black';
    ctx.fillRect(0, 0, img_w, img_h);

    for (let i = 0; i < 300; i++) {
      ctx.fillStyle = `rgba(${Math.random() * 255}, ${Math.random() * 255}, ${Math.random() * 255}, 1)`;
      ctx.beginPath();
      ctx.arc(Math.random() * img_w, Math.random() * img_h, 1, 0, 2 * Math.PI);
      ctx.fill();
    }

    for (let i = 0; i < 10; i++) {
      ctx.strokeStyle = `rgba(${Math.random() * 255}, ${Math.random() * 255}, ${Math.random() * 255}, 1)`;
      ctx.beginPath();
      ctx.moveTo(Math.random() * img_w, Math.random() * img_h);
      ctx.lineTo(Math.random() * img_w, Math.random() * img_h);
      ctx.stroke();
    }

    ctx.fillStyle = 'rgba(144, 144, 144, 0.8)';
    ctx.fillRect(0, 0, img_w, img_h);

    ctx.font = font;
    ctx.textAlign = 'left';
    ctx.textBaseline = 'middle';
    ctx.fillStyle = `rgba(${Math.random() * 100}, ${Math.random() * 100}, ${Math.random() * 100}, 1)`;
    const textWidth = ctx.measureText(code).width;
    ctx.fillText(code, (img_w - textWidth) / 2, (img_h + parseInt(font) - 20) / 2);


    this.captcha = code
    this.picUrl = canvas.toDataURL('image/png')
  }
}
}
</script>

<style>
.card {
width: 100%;
height: 100%;
transition: transform 0.8s;
transform-style: preserve-3d;
}
.card-login,
.card-register {
position: absolute;
width: 100%;
height: 100%;
backface-visibility: hidden;
background: rgb(193, 51, 51, 0.6);
border-radius: 10px;
}
.card-login {
transform: rotateY(0deg);
z-index: 2;
}
.card-register {
transform: rotateY(180deg);
z-index: 1;
}
.flipped {
transform: rotateY(180deg);
}

.login-input,
.register-input {
width: 60%;
margin: 0 auto;
}
.login-input-item {
margin-top: 24px;
}
.el-input .el-input__inner {
height: 50px;
font-size: 20px;
}

@media screen and (max-width: 768px) {
  .login-input,
  .register-input {
      width: 90%;
      margin: 0 auto;
  }
}
</style>
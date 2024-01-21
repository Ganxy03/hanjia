<template>
    <div>
        <div v-if="mailLogined == 'false' || mailLogined == null">
            <h2>邮箱验证</h2>
            <div class="input-box">
                <div style="width: 100%;margin-left: 10%;margin-top: 20px;">
                    <el-input v-model="mailAddress" placeholder="请输入邮箱">
                        <template slot="append">@qq.com</template>
                    </el-input>
                    <el-input v-model="yan" placeholder="请输入验证码" style="margin-top: 10px;">
                        <template slot="append">
                            <span id="sendBtn" style="cursor: pointer;" @click="Verification">{{ sendText }}</span>
                        </template>
                    </el-input>
                    <el-button @click="loginBtn" style="float: right;margin-top: 10px;">登陆</el-button>
                </div>
            </div>
        </div>
        <div v-else>
            <img style="border-radius: 50%;" :src="this.qq.img" alt="">
            <h3>欢迎{{ this.qq.name }}</h3>
            <p style="cursor: pointer;" @click="logout">退出</p>
        </div>
    </div>
</template>
<script>
export default {
    data() {
        return {
            mailAddress: '',
            yan: '',
            sendText: '发送',
            countdown: null,
            isCountingDown: false,
            mailLogined: false,
            qq: {
                name: '',
                img: ''
            }
        }
    },
    created() {
        this.mailLogined = localStorage.getItem("mailLogined")
        this.qq.name = localStorage.getItem('name')
        this.qq.img = localStorage.getItem('imgUrl')
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
        getqqInfo() {
            const url = "https://api.dzzui.com/api/qqname?qq=" + this.mailAddress
            fetch(url)
            .then(res => res.json())
            .then(res => {
                localStorage.setItem('name', res.name)
                localStorage.setItem('imgUrl', res.imgurl)
            })
        },
        Verification() {
            if (this.isCountingDown) {
                this.$message({
                    message: '不要发送的太频繁哦',
                    type: 'warning'
                })
                return;
            }

            this.$confirm('确认发送验证码到这个邮箱吗', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                // console.log('用户点击了确定');

                const regex = /^\d{5,11}$/;
                if (regex.test(this.mailAddress)) {
                    // 邮箱地址符合要求，可以继续执行后续操作
                    const MailAddress = this.mailAddress + "@qq.com"
                    // console.log(MailAddress)

                    const url = "http://hanjia-api.ganxy03.cn/send-mail?email=" + MailAddress;
                    fetch(url, {
                        method: 'POST',
                        headers: {
                            'Accept': 'application/json',
                            'Content-Type': 'application/json'
                        },
                    })
                    .then(res => res.text())
                    .then(res => {
                        console.log(res)
                    })



                    this.$message({
                        message: '发送成功，注意查收',
                        type: 'success'
                    })
                    this.Countdown()
                } else {
                    // 邮箱地址不符合要求，提示用户输入正确的邮箱地址
                    this.$message({
                        message: '请输入正确的qq邮箱',
                        type: 'error'
                    });
                    this.mailAddress = ''
                }
            }).catch(() => {
                // console.log('用户点击了取消');
            });
        },
        Countdown() {
            this.countdown = 60;
            this.isCountingDown = true;
            const sendBtn = document.getElementById('sendBtn');
            const timer = setInterval(() => {
                if (this.countdown > 0) {
                    this.countdown--;
                    sendBtn.textContent = `${this.countdown}s`;
                } else {
                    clearInterval(timer);
                    this.countdown = null;
                    sendBtn.textContent = '发送';
                    this.isCountingDown = false;
                }
            }, 1000);
        },
        loginBtn() {

            if(!this.mailAddress) {
                this.$message({
                    message:'请输入邮箱',
                    type:'warning'
                })
            } else if(!this.yan) {
                this.$message({
                    message:'请输入验证码',
                    type:'warning'
                })
            } else {
                const regex_mail = /^\d{5,11}$/;
                const regex_yan = /^\d{6}$/;
                if (regex_mail.test(this.mailAddress)) {
                    if (regex_yan.test(this.yan)) {
                        const MailAddress = this.mailAddress + "@qq.com"
                        const yan = this.yan
                        const url = "http://hanjia-api.ganxy03.cn/check-verification-code?code=" + yan+"&email="+MailAddress;
                        fetch(url, {
                            method: 'POST',
                            headers: {
                                'Accept': 'application/json',
                                'Content-Type': 'application/json'
                            },
                        })
                        .then(res => res.text())
                        .then(res => {
                            if (res === "Verification success") {
                                localStorage.setItem("mailLogined", true)
                                this.getqqInfo()
                                this.$message({
                                    message: '登陆成功',
                                    type: 'success'
                                })
                                setTimeout(function() {
                                    location.reload()
                                }, 1500)
                            } else {
                                this.$message({
                                    message:'验证码失效或错误',
                                    type:'warning'
                                })
                            }
                        })
                    } else {
                        this.$message({
                            message:'请输入正确的验证码',
                            type:'warning'
                        })
                        this.yan = ''
                    }
                } else {
                    this.$message({
                        message:'请输入正确的邮箱',
                        type:'warning'
                    })
                    this.mailAddress = ''
                }
                
            }

            
            
            
        }
    }
}
</script>
<style>
.input-box {
    width: 80%;
}
</style>
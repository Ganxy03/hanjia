<template>

    <div>
        <h1 @click="getAllComment">Case01-评论</h1>
        <span @click="openDialog" style="cursor: pointer;float: right;border: 1px solid #9a9a9a;padding: 1px;background-color: #9a9a9a;color: #fff;">留言</span>
        <div class="carousel-comment">
            <h4 style="border-bottom: 1px solid #fff;font-size: 20px;text-align: left;padding-bottom: 2px;">热评</h4>
            <div style="margin-top: 30px;" v-for="(item, index) in commetList" :key="item.id" :class="['carousel-comment-item', {'active': index === currentCommentIndex}]"> 
                <li style="list-style: none;">
                    <span>{{ item.content }}</span>
                    <br>
                    --<span>{{ item.time }}</span>
                </li>
            </div>
        </div>
            
        <div style="position: relative;">
            <h4 style="border-bottom: 1px solid #fff;font-size: 20px;text-align: left;padding-bottom: 2px;">评论</h4>
            <div v-for="item in paginatedCommetList" :key="item.id">
                <li style="min-height: 30px;list-style: none;margin-bottom: 5px;border-bottom: 1px solid #9a9a9a;display: flex;justify-content: space-between;align-items: center;padding: 2px 0;">
                    <span style="width: 60%;text-align: left;">{{ item.content }}</span>
                    <span style="width: 35%;">--{{ item.time }}</span>
                </li>
            </div>
            <div style="position: fixed;bottom: 6px;">
                <button style="margin-right: 4px;" @click="prevPage">上一页</button>
                
                <span style="border: 1px solid #9a9a9a;padding: 2px;margin: 0 2px;cursor: pointer;" v-for="pageNum in totalPage" :key="pageNum" @click="changePage(pageNum)" :class="['pageNumbtn', {'active': pageNum === currentPage}]">{{ pageNum }}</span>
                <button style="margin-left: 4px;" @click="nextPage">下一页</button>
            </div>
        </div>
       
        <el-dialog title="留言板" :visible.sync="dialogComment" :modal="false">
            <el-input placeholder="写点什么吧..." v-model="form.input"></el-input>
            <div slot="footer">
                <el-button type="primary" @click="saveComment">发布</el-button>
            </div>
        </el-dialog>

       
    </div>
</template>

<script>
export default {
    data() {
        return {
            commetList: [],
            currentCommentIndex: 0,
            currentPage: 1,
            pageSize: 3,
            form: {
                input: ''
            },
            dialogComment: false
        }
    },
    created() {
        this.getAllComment()
    },
    computed: {
        totalPage() {
            return Math.ceil(this.commetList.length / this.pageSize);
        },
        paginatedCommetList() {
            const start = (this.currentPage - 1) * this.pageSize;
            const end = start + this.pageSize;
            return this.commetList.slice(start, end);
        },
    },
    methods: {
        saveComment() {
            if(this.form.input) {
                fetch("http://hanjia-api.ganxy03.cn/save", {
                    method: 'POST',
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        content: this.form.input
                    })
                })
                this.$message({
                    message: '发布成功',
                    type: 'success'
                })
                this.form.input = ''
                this.dialogComment = false
                location.reload()
            } else {
                this.$message({
                    message: '不能什么都不写哦',
                    type: 'warning'
                })
            }
        },
        openDialog() {
            this.dialogComment = true
        },
        getAllComment() {
            fetch("http://hanjia-api.ganxy03.cn/all", {
                method: 'GET',
                headers: {
                    'Accept': 'application/json',
                }
            })
            .then(res => res.json())
            .then(res => {
                this.commetList = res
                // console.log(this.commetList)
            })
        },
        commentNextSlide() {
            this.currentCommentIndex = (this.currentCommentIndex + 1) % this.commetList.length;
        },
        prevPage() {
            if (this.currentPage > 1) {
            this.currentPage--;
            }
        },
        nextPage() {
            const maxPage = Math.ceil(this.commetList.length / this.pageSize);
            if (this.currentPage < maxPage) {
            this.currentPage++;
            }
        },
        changePage(pageNum) {
            this.currentPage = pageNum;
        }
    },
    mounted() {
        setInterval(this.commentNextSlide, 2000);
    },
}
</script>

<style>
.carousel-comment {
  position: relative;
  width: 100%;
  height: 100px;
  overflow: hidden;
}
.carousel-comment-item {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  opacity: 0;
  transition: opacity 0.5s ease;
}
.carousel-comment-item.active {
  opacity: 1;
}

.pageNumbtn.active {
  color: #fff;
  background-color: rgb(153, 14, 233);
}
</style>
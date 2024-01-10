<template>
  <div>
    <h2>hi</h2>
    <el-input v-model="input" placeholder="快来评论吧">
      <el-button @click="postComment" slot="append" class="el-icon-s-promotion"></el-button>
    </el-input>

    <ul v-for="item in commentList" :key="item.id">
      <li>{{ item.content }}</li>
    </ul>
  </div>
</template>

<script>
export default {
  data() {
    return {
      input: '',
      commentList: []
    }
  },
  created() {
    this.getAll()
  },
  methods: {
    postComment() {
      const value = {
        content: this.input
      }
      fetch("http://localhost:24001/save", {
        method: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(value)
      })
      .then(response => response.json())
      .then(res => {
        console.log(res)
        window.location.reload()
      })
    },
    getAll() {
      fetch("http://localhost:24001/all")
      .then(response => response.json())
      .then(res => {
        this.commentList = res
      })
    }
  }
}
</script>
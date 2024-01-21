<template>
    <div class="captcha"></div>
  </template>
  
  <script lang="ts">
  import { defineComponent } from "vue";
  
  export default defineComponent({
    name: "GeetestCaptcha",
    props: ["captchaConfig"],
    setup(prop) {
      (window as any).initGeetest4(
        prop.captchaConfig.config,
        prop.captchaConfig.handler
      );
    },
  });
  </script>
  
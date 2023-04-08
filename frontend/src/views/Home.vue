<template>
  <div class="home">
    <el-container>
      <el-header>
        <h1>QuickSearch</h1>
      </el-header>
      <el-container>
        <el-aside width="200px">
            <el-menu
                default-active="1"
                class="el-menu-vertical-demo"
                @open="handleOpen"
                @close="handleClose"

                text-color="#323232"
                active-text-color="#ffd04b">


              <el-menu-item index="1">
                <router-link class="link" to="/Grep">
                  <el-menu-item index="7" >
                    查询文本
                  </el-menu-item>
                </router-link>
              </el-menu-item>

              <el-menu-item index="2">
                <router-link class="link" to="/Find">
                  <el-menu-item index="7" >
                    查询文件
                  </el-menu-item>
                </router-link>
              </el-menu-item>

            </el-menu>
        </el-aside>


        <el-main  >
          <router-view style="height: 100%"/>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
// @ is an alias to /src

export default {
  name: 'Home',
}
</script>

<style scoped>

.el-header {
  height: 70px;
}
.el-aside {
  height: calc(100vh - 70px);
}
.el-main {
  padding: 0;
  height: calc(100vh - 70px);
}
</style>

<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

/*
html{
  filter: invert(1) hue-rotate(180deg);
}
*/


#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

body {
  margin: 0;
  padding: 0;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
</style>

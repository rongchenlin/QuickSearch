<template>
  <div >



    <div >
    <el-checkbox style="margin-left: -250px" v-model="ignore">忽略大小写</el-checkbox>
    <el-input style="width: 300px;margin-left: 20px" @keydown="sendFind" v-model="keywords" placeholder="请输入关键字"></el-input>
    <el-select
        @change="loadData"
        style="margin-left: 20px"
        v-model="path" placeholder="请选择路径">
      <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value">
      </el-option>
    </el-select>


      <el-cascader
          style="margin-left: 10px;"
          :options="options2"
          v-model="selectedValues"
          change-on-select
          :props="{ expandTrigger: 'click', value: 'path', label: 'name', children: 'children', isLeaf: 'isFile' }"
          @change="loadData2"
          :show-all-levels="false"
      />

  </div>
  <div style="margin-top: 15px">
    <el-switch
        style="width: 150px;margin-left: -835px"
        v-model="type"
        active-color="#13ce66"
        inactive-color="#ff4949"
        active-text="文件"
        inactive-text="目录">
    </el-switch>
    <el-input style="margin-left:20px; width: 150px;" v-if="this.type===true" @keydown="sendFind" v-model="suffix" placeholder="文件后缀">
    </el-input>
    </div>
    <div>
      <el-button @click="sendFind" >搜索</el-button>
    </div>


    <el-table
        height="calc(100vh - 150px)"
        :data="tableData"
        :span-method="objectSpanMethod"
        border
        style="width: 100%; margin-top: 20px">
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
              size="mini"
              @click="handleEdit(scope.$index, scope.row)">打开文件
          </el-button>
        </template>
      </el-table-column>

      <el-table-column
          prop="fileName"
          label="文件名"
          width="1150">
      </el-table-column>

    </el-table>

  </div>

</template>

<script>

import {postRequest} from "@/utils/api";
import {getRequest} from "@/utils/api";



export default {
  name: 'Find',
  data() {
    return {
      options2: [],
      path2: [],
      options: [{
        value: 'C:\\',
        label: 'C盘'
       },{
        value: 'D:\\',
        label: 'D盘'
      },{
        value: 'E:\\',
        label: 'E盘'
      },{
        value: 'F:\\',
        label: 'F盘'
      }],
      type: true,
      ignore: true,
      searchType: true,
      keywords: 'Linux',
      path: 'E:\\Doc\\Note',
      tableData: [],
      textarea: '',
      suffix: '',
      selectedValues: [],
    }
  },
  mounted() {
    this.loadData(this.path)
  },
  methods: {
    loadData2(){
      this.path = this.selectedValues[0]
      var params = {
        "path": this.path,
      };
      postRequest("/api/files", params).then(resp => {
        if (resp) {
          console.log(resp.data)
          const data = resp.data
          const result = []
          for (let i = 0; i < data.length; i++) {
            const fileInfo = data[i]
            const item = {
              name: fileInfo.name,
              isFile: fileInfo.isFile,
              path: fileInfo.path,
              children: []
            }
            if (!fileInfo.isFile) {
              item.children.push({
                name: '没有下一级目录',
                isLeaf: false
              })
            }
            result.push(item)
          }
          this.options2 = result
        }
      })
    },
    loadData(){
      var params = {
        "path": this.path,
      };
      postRequest("/api/files", params).then(resp => {
        if (resp) {
          console.log(resp.data)
          const data = resp.data
          const result = []
          for (let i = 0; i < data.length; i++) {
            const fileInfo = data[i]
            const item = {
              name: fileInfo.name,
              isFile: fileInfo.isFile,
              path: fileInfo.path,
              children: []
            }
            if (!fileInfo.isFile) {
              item.children.push({
                name: '没有下一级目录',
                isLeaf: false
              })
            }
            result.push(item)
          }
          this.options2 = result
        }
      })
    },
    handleEdit(index, row) {
      console.log(row);
      var params = {
        "fileName": row.fileName,
      };
      postRequest("/api/openFile", params).then(resp => {
        if (resp) {
          console.log(resp.data)
        }
      })
    },
    sendFind() {
      var optionStr = ""
      if (this.ignore === true) {
        optionStr = "i"
      }

      var findType = 'f'
      if (this.type === false) {
        findType = 'd'
      }
      var params = {
        "keywords": this.keywords,
        "path": this.path,
        "type": findType,
        "suffix":this.suffix,
        "optionStr": optionStr
      };

      //创建loading对象开始遮罩
      const rLoading = this.openLoading();
      postRequest("/api/find", params).then(resp => {
        if (resp) {
          rLoading.close();
          this.tableData = resp.data.data;
        }
      })

    },
    arraySpanMethod({row, column, rowIndex, columnIndex}) {
      if (rowIndex % 2 === 0) {
        if (columnIndex === 0) {
          return [1, 2];
        } else if (columnIndex === 1) {
          return [0, 0];
        }
      }
    },
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}

a {
  color: #0b41af;
  text-decoration: none;
}

/deep/ .el-button {
  font-size: 15px;
  color: #0b41af;
}


</style>

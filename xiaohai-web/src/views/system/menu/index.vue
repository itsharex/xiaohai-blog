<template>
  <div class="app-container">
    <el-card class="box-card box-card-height">
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            v-if="$store.getters.permission.includes('system:menu:add')"
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
          >新增
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="info"
            plain
            icon="el-icon-sort"
            size="mini"
            @click="handleExpansion"
          >展开/关闭
          </el-button>
        </el-col>
        <span style="float: right">
          <el-col :span="1.5">
            <el-tooltip class="item" effect="dark" content="清空" placement="top-start">
              <el-button icon="el-icon-delete" size="mini" circle style="min-width: 0;" @click="resetQuery" />
            </el-tooltip>
          </el-col>
          <el-col :span="1.5">
            <el-input
              v-model="queryParams.menuName"
              placeholder="请输入菜单名称"
              clearable
              size="small"
              style="width: 140px"
              @input="handleQuery"
            />
          </el-col>
          <el-col :span="1.5">
            <el-select
              v-model="queryParams.status"
              placeholder="状态"
              clearable
              size="small"
              style="width: 100px;"
              @clear="queryParams.status = null"
              @change="handleQuery"
            >
              <el-option
                v-for="dict in $store.getters.dict.sys_normal_disable"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-col>
          <el-col :span="1.5">
            <el-tooltip class="item" effect="dark" content="刷新" placement="top-start">
              <el-button icon="el-icon-refresh" size="mini" style="min-width: 0;" circle @click="handleQuery" />
            </el-tooltip>
          </el-col>
        </span>
      </el-row>
      <el-table
        v-if="refreshTable"
        v-loading="loading"
        :data="menuList"
        class="table-margin-top-height"
        row-key="id"
        :default-expand-all="expansion"
        :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
      >
        <el-table-column prop="menuName" label="菜单名称" align="center" width="150" />
        <el-table-column prop="icon" label="菜单图标" align="center" width="90">
          <template slot-scope="scope">
            <svg-icon
              v-if="scope.row.icon && scope.row.icon.indexOf('el-icon')== -1"
              :icon-class="scope.row.icon"
            />
            <i v-else-if="scope.row.icon" slot="prefix" :class="scope.row.icon" />
          </template>
        </el-table-column>
        <!--        <el-table-column prop="path" label="路由地址" align="center" />-->
        <el-table-column prop="perms" label="权限标识" align="center" />
        <el-table-column prop="component" label="路径" align="center" />
        <el-table-column label="状态" align="center" prop="status">
          <template slot-scope="scope">
            <dict-tag :options="$store.getters.dict.sys_normal_disable" :value="scope.row.status" width="150" />
          </template>
        </el-table-column>
        <el-table-column label="创建时间" align="center" prop="createdTime" />
        <el-table-column label="顺序" align="center" width="150">
          <template slot-scope="scope">
            <el-input-number v-model="scope.row.menuSort" style="width: 100px" size="mini" :min="0" @change="sort(scope.row)" />
            <!--            <div class="custom-input-number">-->
            <!--              <div class="buttons">-->
            <!--                <el-button-->
            <!--                  class="el-button-margin-left"-->
            <!--                  size="mini"-->
            <!--                  type="text"-->
            <!--                  :disabled="scope.row.menuSort === min"-->
            <!--                  icon="el-icon-minus"-->
            <!--                  @click="sort(scope.row,'-')"-->
            <!--                />-->
            <!--                <span> {{ scope.row.menuSort }}</span>-->
            <!--                <el-button-->
            <!--                  class="el-button-margin-left"-->
            <!--                  size="mini"-->
            <!--                  type="text"-->
            <!--                  icon="el-icon-plus"-->
            <!--                  @click="sort(scope.row,'+')"-->
            <!--                />-->
            <!--              </div>-->
            <!--            </div>-->
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="200">
          <template slot-scope="scope">
            <el-button
              v-if="$store.getters.permission.includes('system:menu:update')"
              class="el-button-margin-left"
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
            >修改
            </el-button>
            <el-button
              v-if="$store.getters.permission.includes('system:menu:add')"
              class="el-button-margin-left"
              size="mini"
              type="text"
              icon="el-icon-plus"
              @click="handleAdd(scope.row)"
            >新增
            </el-button>
            <el-button
              v-if="$store.getters.permission.includes('system:menu:delete')"
              class="el-button-margin-left"
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
            >删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <MenuDialog ref="menuDialog" @closeDialog="closeDialog" />
    </el-card>
  </div>

</template>

<script>
import MenuDialog from './componets/menuDialog.vue'
import { listMenu, delMenu, getMenu, updateMenu } from '@/api/system/menu'

export default {
  name: 'Index',
  components: { MenuDialog },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 是否展开
      expansion: false,
      // 重新渲染表格状态
      refreshTable: true,
      // 菜单表格数据
      menuList: [],
      // 查询参数
      queryParams: {
        menuName: null,
        status: null
      },
      min: 0
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询菜单类型列表 */
    getList() {
      this.loading = true
      listMenu(this.queryParams).then(response => {
        this.menuList = response.data
        this.loading = false
      })
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams = Object.assign({}, this.$options.data().queryParams)
      this.handleQuery()
    },
    /** 新增按钮操作 */
    handleAdd(row) {
      this.$refs.menuDialog.reset()
      if (row != null && row.id) {
        this.$refs.menuDialog.form.parentId = row.id
        if (row.parentId === 0) {
          this.$refs.menuDialog.form.menuType = 'C'
        } else {
          this.$refs.menuDialog.form.menuType = 'F'
        }
      } else {
        this.$refs.menuDialog.form.menuType = 'M'
      }
      this.$refs.menuDialog.menuOptions = this.menuList
      this.$refs.menuDialog.open = true
      this.$refs.menuDialog.title = '添加菜单'
    },
    /** 展开关闭 */
    handleExpansion() {
      this.refreshTable = false
      this.expansion = this.expansion !== true
      this.$nextTick(() => {
        this.refreshTable = true
      })
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      const id = row.id
      getMenu(id).then(response => {
        if (this.$refs.menuDialog.$refs['form'] !== undefined) {
          this.$refs.menuDialog.$refs['form'].resetFields()
        }
        this.$refs.menuDialog.menuOptions = this.menuList
        this.$refs.menuDialog.form = response.data
        this.$refs.menuDialog.open = true
        this.$refs.menuDialog.title = '修改菜单'
      })
    },

    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id
      this.$confirm('是否确认删除菜单编号为"' + ids + '"的数据项？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delMenu(ids).then(response => {
          this.$message.success(response.msg)
          this.getList()
        })
      }).catch(() => {
        this.$message.info('已取消删除')
      })
    },
    /** 回调*/
    closeDialog() {
      this.getList()
    },
    sort(row, operation) {
      // if (operation === '-') {
      //   row.menuSort = row.menuSort - 1
      // }
      // if (operation === '+') {
      //   row.menuSort = row.menuSort + 1
      // }
      const form = {
        id: row.id,
        menuSort: row.menuSort
      }
      updateMenu(form).then(response => {
        this.$message.success(response.msg)
        this.menuList = this.sortMenuList(this.menuList)
      })
    },
    // 排序
    sortMenuList(list) {
      return list.slice().sort((a, b) => a.menuSort - b.menuSort).map(item => {
        if (item.children && item.children.length > 0) {
          item.children = this.sortMenuList(item.children)
        }
        return item
      })
    }

  }
}
</script>
<style scoped>
.custom-input-number {
  display: flex;
  align-items: center;
  flex-direction: column;
}

.buttons {
  display: flex;
  text-align: center;
  flex-direction: row;
  align-items: center;
}
</style>

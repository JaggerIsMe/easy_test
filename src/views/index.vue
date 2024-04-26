<template>
  <div>
    <div>
      <el-form :model="searchFormData" label-width="80px" @submit.prevent>
        <el-row :gutter="5">
          <el-col :span="5">
            <!--input输入-->
            <el-form-item label="手机号">
              <el-input
                clearable
                placeholder="支持模糊搜索"
                v-model.trim="searchFormData.phoneFuzzy"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <!--input输入-->
            <el-form-item label="用户名">
              <el-input
                clearable
                placeholder="支持模糊搜索"
                v-model.trim="searchFormData.userNameFuzzy"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-button type="primary" @click="loadData">搜索</el-button>
            <el-button type="primary" @click="showEdit()">新增用户</el-button>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <el-table :data="tableData" style="width: 100%">
      <el-table-column label="用户ID" prop="userId"> </el-table-column>
      <el-table-column label="用户名" prop="userName"> </el-table-column>
      <el-table-column label="手机号" prop="phone"> </el-table-column>
      <el-table-column label="出生年月" prop="birthday"> </el-table-column>
      <el-table-column label="性别" prop="sex">
        <template #default="scope">
          {{ SEX_MAP[scope.row.sex] }}
        </template>
      </el-table-column>
      <el-table-column label="职位" prop="position">
        <template #default="scope">
          {{ POSITION_MAP[scope.row.sex] }}
        </template>
      </el-table-column>
      <el-table-column label="角色" prop="roles">
        <template #default="scope">
          {{ formateRoles(scope.row.roles) }}
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template #default="scope">
          <el-button-group>
            <el-button type="primary" @click="showEdit(scope.row)"
              >修改</el-button
            >
            <el-button type="primary" @click="updatePwd(scope.row.userId)"
              >修改密码</el-button
            >
            <el-popconfirm
              title="你确定要删除吗？"
              @confirm="delUser(scope.row.userId)"
            >
              <template #reference>
                <el-button type="danger">删除</el-button>
              </template>
            </el-popconfirm>
          </el-button-group>
        </template>
      </el-table-column>
    </el-table>
    <div :style="{ 'margin-top': '20px' }">
      <el-pagination
        background
        layout="prev, pager, next"
        :total="total"
        :page-size="pageSize"
        @current-change="pageChange"
      />
    </div>
    <UserEdit ref="userEditRef" @reload="loadData"></UserEdit>

    <UpdatePassword ref="updatePasswordRef"></UpdatePassword>
  </div>
</template>

<script setup>
import UpdatePassword from "./UpdatePassword.vue";
import UserEdit from "./UserEdit.vue";
import { ElMessage } from "element-plus";
import axios from "axios";
import { ref, reactive, getCurrentInstance, nextTick } from "vue";
import { SEX_MAP, POSITION_MAP, ROLES_MAP } from "@/utils/Constants.js";

const api = {
  loadDataList: "/api/userInfo/loadDataList",
  delUser: "/api/userInfo/delUser",
};

const formateRoles = (roles) => {
  let roelArray = roles.split(",");
  let result = [];
  roelArray.forEach((element) => {
    result.push(ROLES_MAP[element]);
  });
  return result.join(",");
};

const searchFormData = ref({});

const tableData = ref([]);
const total = ref(0);
const pageSize = ref(2);
const pageNo = ref(1);

const loadData = async () => {
  const params = Object.assign({}, searchFormData.value);
  params.pageNo = pageNo.value;
  let result = await axios.post(api.loadDataList, params, {
    headers: {
      "Content-Type": "multipart/form-data",
    },
  });
  const data = result.data;
  if (data.status != "success") {
    ElMessage.error(data.info);
    return;
  }
  tableData.value = data.data.list;
  total.value = data.data.totalCount;
  pageSize.value = data.data.pageSize;
};

loadData();

const pageChange = (e) => {
  pageNo.value = e;
  loadData();
};

const userEditRef = ref();
const showEdit = (data) => {
  let param = null;
  if (data) {
    param = Object.assign({}, data);
  }
  userEditRef.value.show(param);
};

const updatePasswordRef = ref();
const updatePwd = (userId) => {
  updatePasswordRef.value.show(userId);
};

const delUser = async (userId) => {
  let result = await axios.post(
    api.delUser,
    {
      userId,
    },
    {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    }
  );
  const data = result.data;
  if (data.status != "success") {
    ElMessage.error(data.info);
    return;
  }
  ElMessage.success("删除成功");
  loadData();
};
</script>

<style lang="scss" scoped>
</style>

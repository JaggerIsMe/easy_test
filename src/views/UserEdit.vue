<template>
  <div>
    <el-dialog
      v-model="dialogVisible"
      :title="title"
      width="30%"
      :close-on-click-modal="false"
    >
      <el-form
        :model="formData"
        :rules="rules"
        ref="formDataRef"
        label-width="80px"
        @submit.prevent
      >
        <!--input输入-->
        <el-form-item label="用户名" prop="userName">
          <el-input
            clearable
            placeholder="请输入用户名"
            v-model.trim="formData.userName"
          ></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input
            clearable
            placeholder="请输入手机号"
            v-model.trim="formData.phone"
          ></el-input>
        </el-form-item>
        <template v-if="!formData.userId">
          <el-form-item label="密码" prop="password">
            <el-input
              clearable
              placeholder="请输入密码"
              v-model.trim="formData.password"
              type="password"
              show-password
            ></el-input>
          </el-form-item>
          <el-form-item label="重复密码" prop="rePassword">
            <el-input
              clearable
              placeholder="请再次输入密码"
              v-model.trim="formData.rePassword"
              type="password"
              show-password
            ></el-input>
          </el-form-item>
          <el-form-item label="出生年月" prop="birthday">
            <el-date-picker
              :style="{ width: '100%' }"
              v-model="formData.birthday"
              type="date"
              placeholder="请选择出生年月"
            />
          </el-form-item>
        </template>

        <!-- 单选 -->
        <el-form-item label="性别" prop="sex">
          <el-radio-group v-model="formData.sex">
            <el-radio :label="item.value" v-for="item in sexArray">{{
              item.text
            }}</el-radio>
          </el-radio-group>
        </el-form-item>
        <!-- 单选 -->
        <el-form-item label="职位" prop="position">
          <el-radio-group v-model="formData.position">
            <el-radio :label="item.value" v-for="item in positionArray">{{
              item.text
            }}</el-radio>
          </el-radio-group>
        </el-form-item>
        <!-- 下拉框 -->
        <el-form-item label="角色" prop="roles">
          <el-checkbox-group v-model="formData.roles">
            <el-checkbox :label="item.value + ''" v-for="item in rolesArray">{{
              item.text
            }}</el-checkbox>
          </el-checkbox-group>
        </el-form-item>
      </el-form>

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitForm"> 确定 </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ElMessage } from "element-plus";
import { ref, reactive, getCurrentInstance, nextTick } from "vue";
import { SEX_MAP, POSITION_MAP, ROLES_MAP } from "@/utils/Constants.js";
import axios from "axios";

const dialogVisible = ref(false);
const title = ref("新增");

const converMap2Array = (dataMap) => {
  const array = [];
  for (let item in dataMap) {
    array.push({
      value: Number.parseInt(item),
      text: dataMap[item],
    });
  }
  return array;
};

const sexArray = ref(converMap2Array(SEX_MAP));
const positionArray = ref(converMap2Array(POSITION_MAP));
const rolesArray = ref(converMap2Array(ROLES_MAP));

const formData = ref({});
const formDataRef = ref();

const regs = {
  password: /^(?=.*\d)(?=.*[a-zA-Z])[\da-zA-Z~!@#$%^&*_]{8,18}$/,
  phone: /^1[3456789]\d{9}$/,
};

const check = (reg, rule, value, callback) => {
  if (!value) {
    callback(new Error(rule.message));
  } else {
    if (reg.test(value)) {
      callback();
    } else {
      callback(new Error(rule.message));
    }
  }
};

const checkPhone = (rule, value, callback) => {
  check(regs.phone, rule, value, callback);
};

const checkPassword = (rule, value, callback) => {
  check(regs.password, rule, value, callback);
};

const checkRePassword = (rule, value, callback) => {
  if (value && formData.value.password !== value) {
    callback(new Error(rule.message));
  } else {
    callback();
  }
};

const rules = {
  userName: [{ required: true, message: "请输入用户名" }],
  phone: [
    { required: true, message: "请输入手机号" },
    {
      validator: checkPhone,
      message: "请输入正确的手机号",
    },
  ],
  password: [
    { required: true, message: "请输入密码" },
    {
      validator: checkPassword,
      message: "密码只能是数字，字母，特殊字符8-18位",
    },
  ],
  rePassword: [
    { required: true, message: "请再次输入密码" },
    {
      validator: checkRePassword,
      message: "两次输入的密码不一致",
    },
  ],
  birthday: [{ required: true, message: "请选择出生年月" }],
  sex: [{ required: true, message: "请选择性别" }],
  position: [{ required: true, message: "请选择职位" }],
  roles: [{ required: true, message: "请选择角色" }],
};

const emit = defineEmits(["reload"]);
const submitForm = () => {
  formDataRef.value.validate(async (valid) => {
    if (!valid) {
      return;
    }
    let params = {};
    Object.assign(params, formData.value);
    params.roles = params.roles.join(",");
    let result = await axios.post("/api/userInfo/saveUser", params, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
    const data = result.data;
    if (data.status != "success") {
      ElMessage.error(data.info);
      return;
    }
    ElMessage.success("保存成功");
    dialogVisible.value = false;
    emit("reload");
  });
};

const show = (data) => {
  dialogVisible.value = true;
  title.value = data ? "修改" : "新增";
  nextTick(() => {
    formDataRef.value.resetFields();
    if (data) {
      data.roles = data.roles.split(",");
    }
    formData.value = Object.assign({}, data || {});
  });
};

defineExpose({
  show,
});
</script>

<style lang="scss" scoped>
</style>

<script setup>
import { BIconRocketTakeoff } from 'bootstrap-icons-vue'


const clickMenuOption = () => {
  const domReference = document.getElementById('buttonSidebarExpandId')
  if (domReference) {
    if (window.getComputedStyle(domReference).display !== "none") {
      domReference.click()
    }
  }
}

</script>

<template>
  

  <nav class="navbar navbar-expand-md navbar-dark bg-dark sticky-top flex-md-nowrap p-0 shadow">
    <div class="container-fluid">
      <router-link class="navbar-brand col-md-3 col-lg-2 me-0 px-3" :to="{ name: 'home' }" @click="clickMenuOption">
        <BIconRocketTakeoff class="me-2 fs-3"/>
                Rabolut V3
      </router-link>
      <button id="buttonSidebarExpandId" class="navbar-toggler" type="button" data-bs-toggle="collapse"
        data-bs-target="#sidebarMenu" aria-controls="sidebarMenu" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>

      <div class="collapse navbar-collapse justify-content-end">
        <ul class="navbar-nav">
          <!-- <li class="nav-item dropdown" v-show="userStore.user"> -->
          <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button"
              data-bs-toggle="dropdown" aria-expanded="false">
              <!-- <img :src="userStore.userPhotoUrl" class="rounded-circle z-depth-0 avatar-img" alt="avatar image"> -->
              <span class="avatar-text">"SAMPLE"</span>
            </a>
            <ul class="dropdown-menu dropdown-menu-dark dropdown-menu-end" aria-labelledby="navbarDropdownMenuLink">
              <li>
                <button class="dropdown-item" @click="clickProfileOption" >
                  <i class="bi bi-person-square"></i>
                  Profile
                </button>
              </li>
              <li>
                <hr class="dropdown-divider">
              </li>
              <li>
                <button class="dropdown-item" @click.prevent="logout">
                  <i class="bi bi-arrow-right"></i>Logout
                </button>
              </li>
            </ul>
          </li>
        </ul>
      </div>
    </div>
  </nav>

  <div class="container-fluid">
    <div class="row">
      <nav id="sidebarMenu" class="col-md-3 col-lg-2 d-md-block bg-light sidebar collapse">
        <div class="position-sticky pt-3">
          <ul class="nav flex-column">
            <li class="nav-item">
              <router-link class="nav-link" :class="{ active: $route.name === 'dashboard' }"
                          :to="{ name: 'dashboard' }" @click="clickMenuOption">
                <i class="bi bi-house"></i>
                Dashboard
              </router-link>
            </li>
          </ul>

          <h6 class="sidebar-heading d-flex justify-content-between align-items-center px-3 mt-4 mb-1 text-muted" >
            <span>General</span>
          </h6>
          
          <ul  class="nav flex-column mb-2">
            <li class="nav-item">
              <router-link class="nav-link" :class="{ active: $route.name === 'ViewProducts' }"
                          :to="{ name: 'ViewProducts' }" @click="clickMenuOption">
                <i class="bi bi-house"></i>
                Products
              </router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link" :class="{ active: $route.name === 'ViewOrders' }"
                          :to="{ name: 'ViewOrders' }" @click="clickMenuOption">
                <i class="bi bi-house"></i>
                Orders
              </router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link" :class="{ active: $route.name === 'ViewPackages' }"
                          :to="{ name: 'ViewPackages' }" @click="clickMenuOption">
                <i class="bi bi-house"></i>
                Packages
              </router-link>
            </li>
            <li class="nav-item">
              <!-- <router-link class="nav-link" :class="{ active: $route.name === 'MakeTransaction' }"
                          :to="{ name: 'MakeTransaction' }" @click="clickMenuOption">
                <i class="bi bi-house"></i>
                Sensors
              </router-link> -->
            </li>
          </ul>
        </div>
      </nav>
    </div>
  </div>

  <main class="ms-sm-auto col-lg-10 px-md-4">
    <router-view></router-view>
  </main>
</template>


<style scoped>
@import "./assets/dashboard.css";

.avatar-img {
  margin: -2rem 0.8rem -2rem 0.8rem;
  width: 2.3rem;
  height: 2.3rem;
}

.avatar-text {
  line-height: 2.2rem;
  margin: 1rem 0.5rem -2rem 0;
  padding-top: 1rem;
}

.dropdown-item {
  font-size: 0.875rem;
}

.btn:focus {
  outline: none;
  box-shadow: none;
}

#sidebarMenu {
  overflow-y: auto;
}
</style>

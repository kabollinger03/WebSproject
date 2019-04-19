<nav class="navbar navbar-expand-lg navbar-dark" style="background: rgb(0,102,161);">
  <div class="container">
    <a class="navbar-brand" href="#">
      <!--<i class="fas fa-rocket"></i>-->
      <img height="50px" position="inline-block" src="resources/img/logo_1.png"/>
    </a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav mr-auto">
        <li class="nav-item">
          <a class="nav-link" href="streams.htm">Admin <span class="sr-only">(current)</span></a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="createclass.htm">Instructor</a>
        </li>
      </ul>
      <ul class="navbar-nav d-md-none d-lg-block">
        <div class="btn-group mr-3">
          <button style="color: #fff;" class="btn btn-sm dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
           <small> <i class="fas fa-user pr-1"> </i><%String username = (String)session.getAttribute("username"); out.println(username);%> </small>
          </button>
          <div class="dropdown-menu">
            <a class="dropdown-item" href="#"><small><i class="fas fa-lock pr-2"></i>Change Password</small></a>
            <div class="dropdown-divider"></div>
            <a class="dropdown-item" href="login.htm"><small>Logout</small></a>
          </div>
        </div>
      </ul>

    </div>
  </div><!-- /Container -->
</nav>
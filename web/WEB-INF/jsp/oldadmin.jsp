<jsp:include page="head-tag.jsp"/>

<body class="bg-light">

  <jsp:include page="nav.jsp"/>
  <div class="container-fluid">
    <div class="container mt-2 pt-4 pb-3">
      <nav aria-label="breadcrumb">
        <ol class="breadcrumb" style="background: transparent;">
          <li class="breadcrumb-item"><a href="#">Admin</a></li>
          <li class="breadcrumb-item active" aria-current="page">Users</li>
        </ol>
      </nav>
    </div>
  </div>

  <!-- Tabs -->
  <div class="container-fluid">
    <div class="container">
      <ul class="nav nav-tabs">
        <li class="nav-item">
          <a class="nav-link active" href="admin.htm">Users</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="streams.htm">Streams</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="courses.htm">Courses</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="manage-module.htm">Modules</a>
        </li>
      </ul>
    </div>
  </div>

  <div class="container-fluid bg-white" style="min-height: 100vh;">
    <div class="container pb-5">

      <div class="row py-3">
        <div class="col-lg-12">
          <form action="">
          <div class="form">
            <div class="form-row">
              <div class="col-lg-2">
                <button class="btn btn-small btn-success no-border" type="submit"><small><i class="fas fa-plus pr-2"></i>Insert User</small></button>
              </div>
              <div class="col-lg-7">
                <input type="email" class="form-control" placeholder="Email">
              </div>
              <div class="col-lg-3">
                <div class="form-group pt-lg-2 pl-lg-2">
                  <div class="custom-control custom-radio custom-control-inline">
                    <input type="radio" class="custom-control-input" id="customRadio" name="example" value="customEx">
                    <label class="custom-control-label" for="customRadio"><small>Admin</small></label>
                  </div>
                  <div class="custom-control custom-radio custom-control-inline">
                    <input type="radio" class="custom-control-input" id="customRadio2" name="example" value="customEx">
                    <label class="custom-control-label" for="customRadio2"><small>Instructor</small></label>
                  </div> 
                </div>
              </div>
            </div>
          </div>
        </form>
        </div>
      </div>

      <table class="table table-striped table-bordered">
        <thead>
          <tr>
            <th scope="col" style="width: 10%;">#</th>
            <th scope="col">Email</th>
            <th scope="col">Admin</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <th scope="row">1</th>
            <td><a href="manage-user.jsp?id=kevin@syntelinc.com">kevin@syntelinc.com</a></td>
            <td>Yes</td>
          </tr>
          <a href=""><tr>
            <th scope="row">2</th>
            <td><a href="manage-user.jsp?id=chris@syntelinc.com">chris@syntelinc.com</a></td>
            <td>No</td>
          </tr></a>
          <tr>
            <th scope="row">3</th>
            <td><a href="manage-user.jsp?id=nytony@syntelinc.com">nytony@syntelinc.com</a></td>
            <td>Yes</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
  <!-- /Tabs -->

  <!-- Optional JavaScript -->

  <!-- jQuery -->
  <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
  <!-- Popper.js -->
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
  <!-- Bootstrap.js -->
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

</body>
</html>
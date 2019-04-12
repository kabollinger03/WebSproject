<jsp:include page="head-tag.jsp"/>

<body class="bg-light">

<jsp:include page="nav.jsp"/>
  <div class="container-fluid">
    <div class="container mt-2 pt-4 pb-3">
      <nav aria-label="breadcrumb">
        <ol class="breadcrumb" style="background: transparent;">
          <li class="breadcrumb-item"><a href="#">Admin</a></li>
          <li class="breadcrumb-item active" aria-current="page">Courses</li>
        </ol>
      </nav>
    </div>
  </div>

  <!-- Tabs -->
  <div class="container-fluid">
    <div class="container">
      <ul class="nav nav-tabs">
        <li class="nav-item">
          <a class="nav-link" href="admin.jsp">Users</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="streams.jsp">Streams</a>
        </li>
        <li class="nav-item">
          <a class="nav-link active" href="courses.jsp">Courses</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="modules.jsp">Modules</a>
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
                <button class="btn btn-small btn-success no-border" type="submit"><small><i class="fas fa-plus pr-2"></i>Insert Course</small></button>
              </div>
              <div class="col-lg-5">
                <input type="text" class="form-control" placeholder="Course Name">
              </div>
              <div class="col-lg-5">
                <input type="text" class="form-control" placeholder="Module Name">
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
            <th scope="col">Course Name</th>
            <th scope="col">Module Name</th>
          </tr>
        </thead>
        <tbody>
            <tr>
              <th scope="row">1</th>
              <td><a href="manage-course.jsp?id=Big+Name&x=FFD-332">Big Name</a></td>
              <td>sample-123</td>
            </tr>
          <tr>
            <th scope="row">2</th>
            <td><a href="manage-course.jsp?id=Another+Big+Name&x=PSC-328">Another Big Name</a></td>
            <td>sample-123</td>
          </tr>
          <tr>
            <th scope="row">3</th>
            <td><a href="manage-course.jsp?id=Easy+Clap&x=PDM-551">Easy Clap</a></td>
            <td>sample-123</td>
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
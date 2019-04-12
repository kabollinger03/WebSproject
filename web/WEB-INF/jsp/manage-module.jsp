

<jsp:include page="head-tag.jsp"/>

<body class="bg-light">

  <jsp:include page="nav.jsp"/>
  <div class="container-fluid">
    <div class="container mt-2 pt-4 pb-3">
      <nav aria-label="breadcrumb">
        <ol class="breadcrumb" style="background: transparent;">
          <li class="breadcrumb-item"><a href="admin.jsp">Admin</a></li>
          <li class="breadcrumb-item active">Modules</li>
          <li class="breadcrumb-item active" aria-current="page">Manage Module</li>
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
          <a class="nav-link" href="courses.htm">Courses</a>
        </li>
        <li class="nav-item">
          <a class="nav-link active" href="modules.jsp">Modules</a>
        </li>
      </ul>
    </div>
  </div>

  <div class="container-fluid bg-white" style="min-height: 100vh;">
    <div class="container pb-5">

      <div class="row justify-content-center">
        <div class="card col-md-6 col-sm-12 col-lg-5 mt-4 py-3 shadow">
          <div class="card-header text-muted noto bg-white">
            <i class="fas fa-layer-group pr-2"></i> Manage Module 
            <span style="float: right;">
              <form>
                <a href="" class="btn btn-sm btn-danger">
                  <span style="white-space: nowrap;"><i class="fas fa-user-minus"></i> Delete </span>
                </a>
              </form>
            </span>
          </div>
          <form>
            <div class="form-group row mt-3">
              <label for="new_module" class="col-sm-3 col-form-label">Module</label>
              <div class="col-sm-9">
                <input type="text" class="form-control" id="new_module" placeholder="${param.id}">
              </div>
            </div>

            <div class="form-group row mt-3">
              <label for="new_stream_id" class="col-sm-3 col-form-label">Stream ID</label>
              <div class="col-sm-9">
                <input type="email" class="form-control" id="new_stream_id" placeholder="${param.x}">
              </div>
            </div>

            <div class="row justify-content-center mt-1">
              <div class="row pt-3">
                <div class="col-6">
                  <button class="btn btn-sm ghost" type="submit">
                      <span style="white-space: nowrap;"><i class="fas fa-user-edit"></i> Update </span>
                  </button>
                </div>
              </div>
            </div><!-- /row -->
          </form>
        </div>
      </div>
    </div>
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
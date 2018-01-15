<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="container">
  <h2>
    Create a new blog post
  </h2>

  <form method="POST" action="/create">

    <div>
      <label for="name">Nazwa</label>
      <input type="text" name="name" id="name" size="40" value="${fn:escapeXml(event.name)}" class="form-control" />
    </div>

    <div>
      <label for="data">Data</label>
      <input type="date" name="data" id="data" size="40" value="${fn:escapeXml(event.data)}" class="form-control" />
    </div>

    <div>
      <label for="description">Opis</label>
      <textarea name="description" id="description" rows="10" cols="50" class="form-control">${fn:escapeXml(event.content)}</textarea>
    </div>

    <button type="submit">Save</button>
  </form>
</div>
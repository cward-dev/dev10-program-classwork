import { useState } from "react";

function Movie( { title, releaseYear } ) {

  return (
    <div>
      <ul>
        <li>{title} ({releaseYear})</li>
      </ul>
    </div>
  );
}

export default Movie;
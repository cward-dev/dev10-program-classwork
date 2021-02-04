import { useState } from "react";

const initialMoviesData = [
  { id: 1, title: 'Toy Story', releaseYear: 1995 },
  { id: 2, title: 'The Iron Giant', releaseYear: 1999 },
  { id: 3, title: 'Spider-Man: Into the Spider-Verse', releaseYear: 2018 },
];

function deleteMovie( {movieId} ) {
  // TODO after sharing state lesson
}

function Movies() {
  const [movies, setMovies] = useState(initialMoviesData);

  const handleClick = () => {
    const newMovies = [...movies];
    const nextId = Math.max(...movies.map(m => m.id)) + 1;

    newMovies.push({
      id: nextId,
      title: 'The Lion King',
      releaseYear: '1994'
    });

    setMovies(newMovies);
  }

  return (
    <div>
      <ul>
        <button onClick={handleClick}>Add Movie</button>
        {movies.map((movie) =>
          <li key={movie.id}>{movie.title} ({movie.releaseYear})</li>
        )}
      </ul>
    </div>
  );
}

export default Movies;
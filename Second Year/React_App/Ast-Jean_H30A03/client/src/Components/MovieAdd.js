import React, { useState } from "react";
import "../styles/movieAdd.css";

export default function MovieAdd(props) {
  const [Title, setTitle] = useState(null);
  const [actorsString, setActors] = useState(null);
  const [genreString, setGenres] = useState(null);
  const [Year, setYear] = useState(null);
  const [Runtime, setRuntime] = useState(undefined);
  const [Revenue, setRevenue] = useState(undefined);

  const submitForm = (e) => {
    e.preventDefault();
    let Actors = actorsString.split(",");
    let Genre = genreString.split(",");
    let Key = props.movies.length + 1;
    const movie = { Key, Title, Genre, Actors, Year, Runtime, Revenue };
    fetch("/movies", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: movie,
    }).then(() => {
      console.log("A new Movie has Been Added");
    });
  };

  return (
    <div className="MovieAdd">
      <h2>Add a New Movie</h2>
      <form onSubmit={submitForm}>
        <div id="form">
          <label>Title:</label>
          <input
            type="text"
            required
            value={Title}
            onChange={(e) => setTitle(e.target.value)}
          ></input>
          <label>Genres:</label>
          <input
            type="text"
            required
            value={genreString}
            onChange={(e) => {
              setGenres(e.target.value);
            }}
          ></input>
          <label>Actors:</label>
          <input
            type="text"
            required
            value={actorsString}
            onChange={(e) => {
              setActors(e.target.value);
            }}
          ></input>
          <label>Year:</label>
          <input
            type="Number"
            required
            value={Year}
            onChange={(e) => setYear(e.target.value)}
          ></input>
          <label>Runtime:</label>
          <input
            type="Number"
            required
            value={Runtime}
            onChange={(e) => setRuntime(parseInt(e.target.value))}
          ></input>
          <label>Revenue:</label>
          <input
            type="Number"
            required
            value={Revenue}
            onChange={(e) => setRevenue(parseInt(e.target.value))}
          ></input>
        </div>
        <div id="btnAdd">
          <button>Add Movie</button>
        </div>
      </form>
    </div>
  );
}

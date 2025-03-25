import React from "react";
import "../styles/list.css";

export default function List(props) {
  return (
    <div id="movieListDiv">
      {props.movies.map((movie, i) => (
        <div className={"movieDiv"} key={i} onClick={() => props.movieSelect(movie.Key)}>
          <p className={"info"}>
            <span id="movieTitle">{movie.Title}</span>
            <br></br>
            <span>{movie.Year}</span>
          </p>
        </div>
      ))}
    </div>
  );
}

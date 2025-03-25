import React from "react";

export default function Genre(props) {
  return (
    <div id="genreComponent">
      <p id="genreInfo"><b>Genres</b></p>
      {props.genres.map((genre, i) => (
        <div key={i}>
          <p>{genre}</p>
        </div>
      ))}
    </div>
  );
}

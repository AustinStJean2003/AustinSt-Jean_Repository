import React from "react";

export default function YearSelect(props) {
  return (
    <div className={"yearSelect"}>
      <label htmlFor={"year"}><b>Year: </b></label>
      <input
        id={"yearInput"}
        type="number"
        onChange={(e) => props.fetchYearMovies(`${e.target.value}`)}
      />
    </div>
  );
}

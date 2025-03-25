import React, { useEffect, useState } from "react";
import List from "./List";
import Movie from "./Movie";
import ActorSelect from "./ActorSelect";
import YearSelect from "./YearSelect";
import MovieAdd from "./MovieAdd";
import "../styles/App.css";

export default function App() {
  const [movieData, setMovieData] = useState([{}]);
  const [movie, setMovie] = useState(undefined);
  const [actorData, setActorMovie] = useState([{}]);
  const [yearData, setYearMovie] = useState([{}]);
  const [booleanActor, changeBooleanActor] = useState(false);
  const [booleanYear, changeBooleanYear] = useState(false);

  const changeBooleanActorState = (newState) =>
    changeBooleanActor((previousState) => (previousState = newState));

  const changeBooleanYearState = (newState) =>
    changeBooleanYear((previousState) => (previousState = newState));

  const movieSelect = async (id) => {
    if (id === -1 || (movie && id === movie.Key)) {
      setMovie(undefined);
    } else {
      const movieData = await (await fetch(`/movies/${id}`)).json();
      setMovie(movieData);
    }
  };

  const fetchActorMovies = async (name) => {
    if (name === "") {
      changeBooleanActorState(false);
      setActorMovie([]);
    } else {
      const actorData = await (await fetch(`/actors/${name}`)).json();
      changeBooleanActorState(true);
      setActorMovie(actorData);
    }
  };

  const fetchYearMovies = async (year) => {
    if (year === "") {
      changeBooleanYearState(false);
      setYearMovie([]);
    } else {
      const yearData = await (await fetch(`/years/${year}`)).json();
      changeBooleanYearState(true);
      setYearMovie(yearData);
    }
  };

  const fetchMovies = () => {
    let movies;
    fetch("/movies")
      .then((response) => response.json())
      .then((data)  => (movies = data))
      .then(() => {
        movies = movies.sort((a, b) => {
          return a.Title.localeCompare(b.Title);
        });
        setMovieData(movies);
      });
  };

  useEffect(() => {
    fetchMovies();
  }, []);

  return (
    <div>
      <div>
        <h3 id="title">St-Jean Movie Library</h3>
      </div>
      {/* <div className="searchBars"></div> */}
      <div id="movieComponent">{movie ? <Movie movie={movie} /> : ""}</div>
      <div id="listComponent">
        <List movieSelect={movieSelect} movies={movieData} />
        <div id="seachBarDiv">
          <div>
            <ActorSelect fetchActorMovies={fetchActorMovies} />
            <p>*Search by part of an actor's name*</p>
          </div>
          <div>
            <YearSelect fetchYearMovies={fetchYearMovies} />
            <p>*Search by year*</p>
          </div>
        </div>

        <div id="searchResultDiv">
          {booleanActor ? (
            <List movieSelect={movieSelect} movies={actorData} />
          ) : (
            <List movieSelect={movieSelect} movies={movieData} />
          )}

          {booleanYear ? (
            <List movieSelect={movieSelect} movies={yearData} />
          ) : (
            <List movieSelect={movieSelect} movies={movieData} />
          )}
        </div>
        <MovieAdd movie = {movieData}/>
      </div>
    </div>
  );
}

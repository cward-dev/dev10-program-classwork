function Errors( { errors } ) {

  return (
    errors.map(error => <div className="alert alert-danger" role="alert">Error: {error}</div>)
  );
}

export default Errors;
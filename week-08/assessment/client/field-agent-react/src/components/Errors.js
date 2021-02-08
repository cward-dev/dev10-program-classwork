function Errors( { errors } ) {
  if (errors.length === 0) {
    return [];
  }

  return (
    <div className="alert alert-danger pt-4">
      <b>--Error--{errors.length > 1 ? 's' : null}</b>
      <ul>
        {errors.map(error => (
          <li key={error}>{error}</li>
        ))}
      </ul>
    </div>
  );
}

export default Errors;
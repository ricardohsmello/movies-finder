async function fetchAndDisplayMovies(data, spinnerDiv) {
    try {

        spinnerDiv.innerHTML = `
            <div class="d-flex justify-content-center align-items-center">
                <div class="spinner-border text-light" role="status" style="width: 3rem; height: 3rem;">
                    <span class="visually-hidden">Loading...</span>
                </div>
            </div>`;

        const response = await fetch('https://movies-finder-vsnw.onrender.com/movies/findSimilar', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });

        spinnerDiv.innerHTML = '';

        return await response.json();
    } catch (error) {
        console.error('Error:', error);
        return [];
    }
}

async function submitForm() {
    const query = document.getElementById('searchQuery').value.trim();
    if (!query) {
        alert('Type something first 🙂');
        return;
    }

    const spinnerDiv = document.getElementById('loaderSpinner');
    const movieData = await fetchAndDisplayMovies({ input: query }, spinnerDiv);
    const resultsDiv = document.getElementById('movieResults');
    resultsDiv.innerHTML = '';

    if (movieData.length === 0) {
        resultsDiv.innerHTML = '<p class="text-center text-light">No movies found.</p>';
        spinnerDiv.innerHTML = '';
    } else {
        movieData.forEach(m => {
            console.log(m)
            const cast = m.cast
                ? m.cast.map(c => `<a href="https://www.google.com/search?q=${encodeURIComponent(c)}" target="_blank">${c}</a>`).join(', ')
                : 'N/A';

            resultsDiv.insertAdjacentHTML('beforeend', `
                <div class="col-md-6 col-lg-4">
                
                   <div class="movie-card h-100 position-relative">
                   <div class="rating-badge">
                    <span class="badge bg-secondary">${m.rating || 'NR'}</span>
                   </div>
                        <h5 class="mb-2">
                            <a href="https://www.imdb.com/find/?q=${encodeURIComponent(m.title)}" target="_blank">
                                ${m.title || 'Untitled'}
                            </a>
                        </h5>
                               <p class="mb-3">
                              <img src="${m.poster}" alt="Poster of ${m.title}" class="movie-poster">
                        </p>
                                            
                        <p class="mb-1"><strong>Year:</strong> ${m.year || '-'}</p>
                        <p class="mb-1"><strong>Genres:</strong> ${m.genres ? m.genres.join(', ') : '-'}</p>
                        <p class="mb-1"><strong>Cast:</strong> ${cast}</p>                               
                        <p class="mt-3 small">${m.plot || ''}</p>
                         
                    </div>
                </div>
            `);
        });
    }

    resultsDiv.scrollIntoView({ behavior: 'smooth' });
}

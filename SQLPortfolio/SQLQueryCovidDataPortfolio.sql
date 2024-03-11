
SELECT *
FROM 
	dbo.CovidDeaths
WHERE 
	continent is not null
order by 
	3,4

SELECT 
	location, 
	date, 
	total_cases, 
	new_cases, 
	total_deaths, 
	population
FROM
	dbo.CovidDeaths
ORDER BY 
	1,2

-- Looking at Total Cases vs. Total Deaths
-- Death Rate by country if you get covid

SELECT 
	location, 
	date, 
	total_cases, 
	total_deaths, 
	ROUND((total_deaths/total_cases) * 100, 2) as DeathPercentage
FROM 
	PortfolioProject.dbo.CovidDeaths
WHERE 
	location like '%Sweden%'
ORDER BY 
	2

-- Highest Mortality rate per country with over 500 cases

SELECT 
	location, 
	MAX(ROUND((total_deaths/total_cases) * 100, 2)) as DeathPercentage
FROM 
	PortfolioProject.dbo.CovidDeaths
WHERE 
	total_cases > '500'
GROUP BY 
	location
ORDER BY 
	DeathPercentage DESC

-- Average Mortality rate --

SELECT 
	location, 
	MAX(ROUND((total_deaths/total_cases) * 100, 2)) as DeathPercentage
FROM 
	PortfolioProject.dbo.CovidDeaths
WHERE 
	total_cases > 500
GROUP BY 
	location
ORDER BY 
	DeathPercentage DESC
-------------------------------------------------

-- Looking at countries with the highest infection rate vs. population
SELECT 
	location as Country, 
	population as CountryPopulation, 
	MAX(total_cases) AS HighestConcurrentInfectionCount, 
	ROUND(MAX((total_cases/population)) * 100,2) AS HighestPercentPopulationInfected
FROM 
	PortfolioProject.dbo.CovidDeaths
GROUP BY 
	location, 
	population
ORDER BY 
	HighestPercentPopulationInfected DESC

-- Showing the countries with the highest death count per population---------
SELECT 
	location as Country, 
	population as CountryPopulation, 
	MAX(CAST(total_deaths as int)) AS TotalDeathCount
FROM 
	PortfolioProject.dbo.CovidDeaths
WHERE 
	continent is not null
GROUP BY 
	location, 
	population 
ORDER BY 
	TotalDeathCount desc


-- By continent | Correct

SELECT 
	location, 
	MAX(CAST(total_deaths as int)) AS TotalDeathCount
FROM 
	PortfolioProject.dbo.CovidDeaths
WHERE 
	continent is null
GROUP BY 
	location
ORDER BY 
	TotalDeathCount desc

-- Showing the continents with the highest death count
SELECT 
	continent, 
	MAX(CAST(total_deaths as int)) AS TotalDeathCount
FROM 
	PortfolioProject.dbo.CovidDeaths
WHERE 
	continent is not null
GROUP BY 
	continent
ORDER BY 
	TotalDeathCount desc

-- GLOBAL NUMBERS
SELECT 
	date, 
	SUM(new_cases) As 'Total Cases', 
	SUM(CAST(new_deaths AS INT)) as 'Total Deaths', 
	ROUND(SUM(CAST(new_deaths AS INT)) / SUM(new_cases) * 100, 2) as DeathProcentage
FROM 
	PortfolioProject.dbo.CovidDeaths
WHERE 
	continent is not null AND new_cases is not null
GROUP BY 
	date
ORDER BY
	1,2 ASC

SELECT
	SUM(new_cases) As 'Total Cases', 
	SUM(CAST(new_deaths AS INT)) as 'Total Deaths', 
	ROUND(SUM(CAST(new_deaths AS INT)) / SUM(new_cases) * 100, 2) as DeathProcentage
FROM 
	PortfolioProject.dbo.CovidDeaths
WHERE 
	continent is not null 
ORDER BY 
	1,2 ASC

-- JOINING the vaccination table on Sweden

SELECT 
	* 
FROM 
	PortfolioProject.dbo.CovidDeaths as da
JOIN 
	PortfolioProject.dbo.CovidVaccinations as vac on da.location = vac.location
	AND da.date = vac.date
WHERE 
	da.location like '%Sweden%'

--DISPLAY Vaccinations by location on dates with population

SELECT 
	da.continent, 
	da.location, 
	da.date, 
	da.population, 
	vac.new_vaccinations
FROM
	PortfolioProject.dbo.CovidDeaths as da
JOIN 
	PortfolioProject.dbo.CovidVaccinations as vac 
	ON da.location = vac.location
	AND da.date = vac.date
WHERE 
	da.continent is not null
ORDER BY 
	2,3

-- Add rolling counter using partition by ordering by date and location

SELECT 
	da.continent, 
	da.location, 
	da.date, 
	da.population, 
	vac.new_vaccinations, 
	SUM(CAST(vac.new_vaccinations as int)) OVER (Partition by da.location Order By da.location, da.date) as RollingVaccinationCounter
FROM 
	PortfolioProject.dbo.CovidDeaths as da
JOIN 
	PortfolioProject.dbo.CovidVaccinations AS vac 
	ON da.location = vac.location
	AND da.date = vac.date
WHERE 
	da.continent is not null
ORDER BY 
	2,3


--USING CTE
WITH PopvsVac (Continent, Location, Date, Population, New_vaccinations, RollingPeopleVaccinated) AS
(
    SELECT 
        da.continent, 
        da.location, 
        da.date, 
        da.population, 
        vac.new_vaccinations, 
        SUM(CAST(vac.new_vaccinations AS INT)) OVER (PARTITION BY da.location ORDER BY da.location, da.date) AS RollingVaccinationCounter
    FROM 
        PortfolioProject.dbo.CovidDeaths AS da
    JOIN 
        PortfolioProject.dbo.CovidVaccinations AS vac ON da.location = vac.location AND da.date = vac.date
    WHERE 
        da.continent IS NOT NULL
)
SELECT 
    *,
	ROUND((RollingPeopleVaccinated/Population)*100,2) as RollingProcentage
FROM 
    PopvsVac

-- TEMP TABLE

DROP TABLE IF EXISTS #PercentPopulationVaccinated
CREATE TABLE #PercentPopulationVaccinated
(
	Continent nvarchar(255),
	Location nvarchar(255),
	Date datetime,
	Population numeric,
	New_vaccinations numeric,
	RollingPeopleVaccinated numeric
)


INSERT INTO #PercentPopulationVaccinated
SELECT 
	da.continent, 
	da.location, 
	da.date, 
	da.population, 
	vac.new_vaccinations, 
	SUM(CAST(vac.new_vaccinations as int)) OVER (Partition by da.location Order By da.location, da.date) as RollingVaccinationCounter
FROM 
	PortfolioProject.dbo.CovidDeaths as da
JOIN 
	PortfolioProject.dbo.CovidVaccinations as vac on da.location = vac.location
	and	
	da.date = vac.date
WHERE 
	da.continent is not null

SELECT *, (RollingPeopleVaccinated/Population)*100
FROM #PercentPopulationVaccinated


-- Creating view to store data for later visualizations

CREATE VIEW PercentPopulationVaccinated 
AS
SELECT 
	da.continent, 
	da.location, 
	da.date, 
	da.population, 
	vac.new_vaccinations, 
	SUM(CAST(vac.new_vaccinations as int)) OVER (Partition by da.location Order By da.location, da.date) as RollingVaccinationCounter
FROM 
	PortfolioProject.dbo.CovidDeaths as da
JOIN 
	PortfolioProject.dbo.CovidVaccinations AS vac 
	ON da.location = vac.location
	AND da.date = vac.date
WHERE 
	da.continent is not null
---- ORDER BY 2,3

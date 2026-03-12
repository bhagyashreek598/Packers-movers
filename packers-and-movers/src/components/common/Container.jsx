// components/layout/Container.jsx
const Container = ({ children, className = "" }) => {
  return (
    <div
      className={`
        w-full
        mx-auto
        px-4
        sm:px-6
        md:px-8
        lg:px-12
        xl:px-16
        2xl:px-38
        ${className}
      `}
    >
      {children}
    </div>
  );
};

export default Container;
